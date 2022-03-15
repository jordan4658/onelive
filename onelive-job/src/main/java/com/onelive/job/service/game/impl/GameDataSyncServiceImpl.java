package com.onelive.job.service.game.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ob.constant.GameTypeConstant;
import com.ob.constant.VersionEnum;
import com.ob.req.GameConfigReq;
import com.ob.req.QueryBetRecordListReq;
import com.ob.resp.BetRecord;
import com.ob.resp.GameConfig;
import com.ob.resp.GameConfigResp;
import com.ob.resp.QueryBetRecordListResp;
import com.ob.resp.betrecord.*;
import com.ob.service.GameService;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.mapper.master.game.GameObgMapper;
import com.onelive.common.mybatis.mapper.master.game.GameRecordMapper;
import com.onelive.common.mybatis.mapper.master.game.GameSyncRecordMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameCategoryMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameObgMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameRecordMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameSyncRecordMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.job.service.game.GameDataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 第三方游戏数据同步服务
 */
@Slf4j
@Service
public class GameDataSyncServiceImpl implements GameDataSyncService {

    @Resource
    private SlaveMemUserMapper slaveMemUserMapper;
    @Resource
    private SlaveGameCategoryMapper slaveGameCategoryMapper;
    @Resource
    private SlaveGameObgMapper slaveGameObgMapper;
    @Resource
    private GameObgMapper gameObgMapper;
    @Resource
    private SlaveGameRecordMapper slaveGameRecordMapper;
    @Resource
    private GameRecordMapper gameRecordMapper;
    @Resource
    private SlaveGameSyncRecordMapper slaveGameSyncRecordMapper;
    @Resource
    private GameSyncRecordMapper gameSyncRecordMapper;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;


    private void syncGameRecordData(GameSyncRecord syncRecord) {
        Integer categoryId = syncRecord.getCategoryId();
        Long sDate = syncRecord.getStartTime().getTime();
        log.info("================================ 开始同步数据 categoryId=>{} =========================", categoryId);
        // 获取分布式写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("syncGameRecordData" + categoryId + sDate);
        try {
            boolean bool = lock.writeLock().tryLock(3, 60, TimeUnit.SECONDS);
            if (bool) {
                //更新记录的次数和状态
                syncRecord.setUpdateTime(new Date());
                slaveGameSyncRecordMapper.updateById(syncRecord);
                //查询分类信息
                QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
                categoryWrapper.lambda().eq(GameCategory::getCategoryId, categoryId).eq(GameCategory::getIsDelete,false);
                GameCategory category = slaveGameCategoryMapper.selectOne(categoryWrapper);
                if (category == null) {
                    throw new BusinessException(StatusCode.PARAM_ERROR);
                }
                String gameType = category.getGameType();
                Long eDate = syncRecord.getEndTime().getTime();
                QueryBetRecordListReq queryBetRecordListReq = new QueryBetRecordListReq();
                queryBetRecordListReq.setHost(category.getDataHost());
                if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
                    if (GameTypeConstant.ZR.equals(gameType)) {
                        queryBetRecordListReq.setAesKey(category.getAesKey());
                    } else {
                        queryBetRecordListReq.setAesKey(category.getIv());
                    }
                }
                queryBetRecordListReq.setMd5Key(category.getMd5Key());
                queryBetRecordListReq.setGameTypeEnum(gameType);
                //接口版本号 默认V1
                queryBetRecordListReq.setVersionEnum(VersionEnum.V1);
                //商户账号
                String merchantCode = category.getMerchantCode();
                queryBetRecordListReq.setMerchantCode(merchantCode);
                //开始时间 13位时间戳
                queryBetRecordListReq.setStartTime(sDate);
                //结束时间 13位时间戳
                queryBetRecordListReq.setEndTime(eDate);
                //只查询已结算订单
                queryBetRecordListReq.setSettleStatus(1);
                //页码
                Integer pageIndex = 1;
                queryBetRecordListReq.setPageIndex(pageIndex);
                //时间戳 当前时间13位时间戳
                queryBetRecordListReq.setTimestamp(System.currentTimeMillis());
                log.info("请求参数:{}", queryBetRecordListReq);
                QueryBetRecordListResp resp = GameService.getGameService().queryBetRecordList(queryBetRecordListReq);

                while (true) {
                    log.info("请求结果:{}", JSONObject.toJSON(resp));
                    if (resp.getCode() != 200) {
                        break;
                    }
                    //保存数据
                    List<BetRecord> list = resp.getRecord();
                    if (CollectionUtil.isNotEmpty(list)) {
                        List<GameRecord> addList = new LinkedList<>();
                        for (BetRecord record : list) {
                            //先查询是否存在该数据
                            CommonProp commonProp = record.getCommonProp();
                            GameRecord dbRecord = slaveGameRecordMapper.selectById(commonProp.getId());
                            if (dbRecord != null) {
                                log.warn("该记录已存在,id={}", commonProp.getId());
                                continue;
                            }
                            GameRecord gameRecord = new GameRecord();
                            BeanCopyUtil.copyProperties(commonProp, gameRecord);
                            DyProp dyProp = record.getDyProp();
                            QpProp qpProp = record.getQpProp();
                            TyProp tyProp = record.getTyProp();
                            ZrProp zrProp = record.getZrProp();
                            if (dyProp != null) {
                                BeanCopyUtil.copyProperties(dyProp, gameRecord);
                            }
                            if (qpProp != null) {
                                BeanCopyUtil.copyProperties(qpProp, gameRecord);
                            }
                            if (tyProp != null) {
                                BeanCopyUtil.copyProperties(tyProp, gameRecord);
                            }
                            if (zrProp != null) {
                                BeanCopyUtil.copyProperties(zrProp, gameRecord);
                                //把真人游戏的用户名处理一下
                                gameRecord.setPlayerName(gameRecord.getPlayerName().replace(merchantCode.toLowerCase(),""));
                            }
                            addList.add(gameRecord);
                            List<BetRecord> betRecordList = record.getBetRecordList();
                            if (CollectionUtil.isNotEmpty(betRecordList)) {
                                String parentId = gameRecord.getId();
                                for (BetRecord betRecord : betRecordList) {
                                    CommonProp commonPropItem = betRecord.getCommonProp();
                                    GameRecord dbRecordItem = slaveGameRecordMapper.selectById(commonPropItem.getId());
                                    if (dbRecordItem != null) {
                                        log.warn("该记录已存在,id={}", commonPropItem.getId());
                                        continue;
                                    }
                                    GameRecord itemRecord = new GameRecord();
                                    BeanCopyUtil.copyProperties(commonPropItem, itemRecord);
                                    DyProp dyPropItem = betRecord.getDyProp();
                                    QpProp qpPropItem = betRecord.getQpProp();
                                    TyProp tyPropItem = betRecord.getTyProp();
                                    ZrProp zrPropItem = betRecord.getZrProp();
                                    if (dyPropItem != null) {
                                        BeanCopyUtil.copyProperties(dyPropItem, itemRecord);
                                    }
                                    if (qpPropItem != null) {
                                        BeanCopyUtil.copyProperties(qpPropItem, itemRecord);
                                    }
                                    if (tyPropItem != null) {
                                        BeanCopyUtil.copyProperties(tyPropItem, itemRecord);
                                    }
                                    if (zrPropItem != null) {
                                        BeanCopyUtil.copyProperties(zrPropItem, itemRecord);
                                        //把真人游戏的用户名处理一下
                                        gameRecord.setPlayerName(gameRecord.getPlayerName().replace(merchantCode.toLowerCase(),""));
                                    }
                                    itemRecord.setParentRecordId(parentId);
                                    addList.add(itemRecord);
                                }
                            }
                        }

                        if (addList.size() > 0) {
                            //批量插入数据, 每次最多插入500条
                            List<GameRecord> insertList = new LinkedList<>();
                            for (int i = 0; i < addList.size(); i++) {
                                insertList.add(addList.get(i));
                                if (i != 0 && i % 500 == 0) {
                                    gameRecordMapper.insertBatch(insertList);
                                    insertList = new LinkedList<>();
                                }
                            }
                            gameRecordMapper.insertBatch(insertList);
                        }
                    }


                    Integer totalPage = resp.getTotalPage();
                    pageIndex = resp.getPageIndex();
                    pageIndex++;
                    if (pageIndex > totalPage) {
                        //更新状态
                        syncRecord.setUpdateTime(new Date());
                        syncRecord.setStatus(1);
                        gameSyncRecordMapper.updateById(syncRecord);
                        break;
                    }
                    queryBetRecordListReq.setPageIndex(pageIndex);
                    //时间戳 当前时间13位时间戳
                    queryBetRecordListReq.setTimestamp(System.currentTimeMillis());
                    resp = GameService.getGameService().queryBetRecordList(queryBetRecordListReq);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("---------------------------syncGameRecordData failure-!!!!!!!!!!-----------------");
        } finally {
            lock.writeLock().unlock();
        }
    }


    /**
     * 查询游戏分类列表
     *
     * @return
     */
    @Override
    public List<GameCategory> listCategory() {
        QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCategory::getIsDelete, false).eq(GameCategory::getIsWork, true);
        return slaveGameCategoryMapper.selectList(queryWrapper);
    }

    /**
     * 查询该分类中未执行的任务数量
     *
     * @param categoryId
     * @return
     */
    @Override
    public int countCategoryJobInFuture(Integer categoryId) {
        QueryWrapper<GameSyncRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameSyncRecord::getCategoryId, categoryId).gt(GameSyncRecord::getStartTime, new Date());
        return slaveGameSyncRecordMapper.selectCount(queryWrapper);
    }

    /**
     * 创建游戏分类的任务数据
     *
     * @param categoryId
     */
    @Transactional
    @Override
    public void createJobData(Integer categoryId) {
        log.info("............categoryId={}.........", categoryId);
        //查询最新一期的任务数据
        QueryWrapper<GameSyncRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameSyncRecord::getCategoryId, categoryId).orderByDesc(GameSyncRecord::getStartTime).last(" LIMIT 1");//时间倒序
        GameSyncRecord gameSyncRecord = slaveGameSyncRecordMapper.selectOne(queryWrapper);
        Calendar calendar = Calendar.getInstance();
        if (gameSyncRecord != null) {
            log.info("............gameSyncRecord is not null categoryId={}.........", categoryId);
            calendar.setTime(gameSyncRecord.getStartTime());
        }
        //每个任务,时间加3秒
        calendar.add(Calendar.SECOND, 3);
        //一次创建1200条数据
        List<GameSyncRecord> list = new LinkedList<>();
        for (int i = 1; i <= 1200; i++) {
            GameSyncRecord nextRecord = new GameSyncRecord();
            nextRecord.setStartTime(calendar.getTime());
            calendar.add(Calendar.SECOND, 3);//结束时间
            nextRecord.setEndTime(calendar.getTime());
            nextRecord.setCategoryId(categoryId);
            nextRecord.setCreateTime(new Date());
            calendar.add(Calendar.SECOND, 1);//下一个开始时间
            list.add(nextRecord);
        }
        slaveGameSyncRecordMapper.insertBatch(list);
        log.info("............createJobData end categoryId={} .......", categoryId);
    }

    /**
     * 查询最新的任务并执行
     *
     * @param categoryId
     */
    @Override
    public void queryNewestJob(Integer categoryId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        QueryWrapper<GameSyncRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GameSyncRecord::getCategoryId, categoryId)
                .eq(GameSyncRecord::getStatus, 0)
                .lt(GameSyncRecord::getStartTime, calendar.getTime())
                .orderByDesc(GameSyncRecord::getStartTime)
                .last(" LIMIT 1");
        GameSyncRecord syncRecord = slaveGameSyncRecordMapper.selectOne(queryWrapper);
        if (syncRecord == null) {
            return;
        }
        syncRecord.setCount(1);
        //执行同步数据
        syncGameRecordData(syncRecord);
    }

    /**
     * 查询已经过期,并且没有执行成功的任务
     */
    @Override
    public void checkExpSyncJob() {
        List<GameCategory> categoryList = listCategory();
        if (CollectionUtil.isNotEmpty(categoryList)) {
            for (GameCategory category : categoryList) {
                Integer categoryId = category.getCategoryId();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MILLISECOND, 0);
                QueryWrapper<GameSyncRecord> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda()
                        .eq(GameSyncRecord::getCategoryId, categoryId)
                        .eq(GameSyncRecord::getStatus, 0)
                        .eq(GameSyncRecord::getCount, 0)
                        .lt(GameSyncRecord::getStartTime, calendar.getTime())
                        .orderByDesc(GameSyncRecord::getStartTime)
                        .last(" LIMIT 1");
                GameSyncRecord syncRecord = slaveGameSyncRecordMapper.selectOne(queryWrapper);
                Date queryDate = new Date();
                if (syncRecord != null) {
                    queryDate = syncRecord.getStartTime();
                }
                QueryWrapper<GameSyncRecord> queryOldWrapper = new QueryWrapper<>();
                queryOldWrapper.lambda()
                        .eq(GameSyncRecord::getCategoryId, categoryId)
                        .eq(GameSyncRecord::getStatus, 0)
                        .lt(GameSyncRecord::getStartTime, queryDate)
                        .orderByAsc(GameSyncRecord::getStartTime)
                        .last(" LIMIT 1");
                GameSyncRecord oldRecord = slaveGameSyncRecordMapper.selectOne(queryOldWrapper);
                if (oldRecord == null) {
                    continue;
                }
                Integer count = oldRecord.getCount() + 1;
                oldRecord.setCount(count);
                if (count > 2) {
                    oldRecord.setStatus(2);//先标记为失败, 如果成功, 会更新状态为成功
                }
                //执行同步数据
                syncGameRecordData(oldRecord);
            }
        }
    }

    /**
     * 同步分类中的游戏列表
     */
    @Transactional
    @Override
    public void syncGameList(GameCategory category) {
        Integer categoryId = category.getCategoryId();
        String gameType = category.getGameType();
        GameConfigReq gameConfigReq = new GameConfigReq();
        gameConfigReq.setHost(category.getInfoHost());
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                gameConfigReq.setAesKey(category.getAesKey());
            } else {
                gameConfigReq.setAesKey(category.getIv());
            }
        }
        gameConfigReq.setMd5Key(category.getMd5Key());

        gameConfigReq.setGameTypeEnum(gameType);
        //接口版本号 默认V1
        gameConfigReq.setVersionEnum(VersionEnum.V1);
        //商户账号
        gameConfigReq.setMerchantCode(category.getMerchantCode());
        //时间戳 当前时间13位时间戳
        gameConfigReq.setTimestamp(System.currentTimeMillis());
        GameConfigResp resp = GameService.getGameService().gameConfig(gameConfigReq);
        if (resp.getCode() == 200) {
            List<GameConfig> list = resp.getGames();
            if (CollectionUtil.isNotEmpty(list)) {
                //平台代码
                String platformCode = category.getPlatformCode();
                List<GameObg> addList = new LinkedList<>();
                List<GameObg> updateList = new LinkedList<>();
                for (GameConfig game : list) {
                    QueryWrapper<GameObg> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda()
                            .eq(GameObg::getPlatformCode,platformCode)
                            .eq(GameObg::getCategoryId,categoryId)
                            .eq(GameObg::getGameId,game.getGameId())
                            .last(" LIMIT 1");

                    GameObg gameObg = slaveGameObgMapper.selectOne(queryWrapper);
                    if (gameObg != null) {
                        //如果状态不相同, 更新游戏的状态
                        if(!gameObg.getStatus().equals(game.getStatus())) {
                            gameObg.setStatus(game.getStatus());
                            gameObg.setUpdateUser("SyncJob");
                            gameObg.setUpdateTime(new Date());
                            updateList.add(gameObg);
                        }
                    } else {
                        GameObg newGame = new GameObg();
                        newGame.setGameId(game.getGameId().longValue());
                        newGame.setIconUrl(game.getIconUrl());
                        newGame.setCreateUser("SyncJob");
                        newGame.setCreateTime(new Date());
                        newGame.setName(game.getGameName());
                        newGame.setCategoryId(categoryId);
                        newGame.setPlatformCode(platformCode);
                        addList.add(newGame);
                    }
                }

                if (addList.size() > 0) {
                    addList.stream().forEach(gameThird -> gameObgMapper.insert(gameThird));
                }
                if (updateList.size() > 0) {
                    updateList.stream().forEach(gameThird -> gameObgMapper.updateById(gameThird));
                    //更新其他表的状态
                }
            }
        }
    }

    /**
     * 查询第三方游戏记录, 生成账变信息
     */
    @Transactional
    @Override
    public void gameRecordCreateAccountChangeInfoJob() {
        QueryWrapper<GameRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameRecord::getAccountChangeStatus,0).last(" LIMIT 500");
        List<GameRecord> list = slaveGameRecordMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            //取出所有id
            List<String> ids = list.stream().map(GameRecord::getId).collect(Collectors.toList());
            UpdateWrapper<GameRecord> updateWrapper=new UpdateWrapper<>();
            updateWrapper.lambda().in(GameRecord::getId,ids).set(GameRecord::getAccountChangeStatus,1);
            //批量更新状态
            gameRecordMapper.update(null, updateWrapper);

            //插入账变记录
            for(GameRecord record:list) {
                QueryWrapper<MemUser> userQueryWrapper=new QueryWrapper<>();
                userQueryWrapper.lambda().eq(MemUser::getAccno,record.getPlayerName()).last(" LIMIT 1 ");
                MemUser memUser = slaveMemUserMapper.selectOne(userQueryWrapper);
                if(memUser==null){
                    continue;
                }
                MemAccountChangeVO chargeVO = new MemAccountChangeVO();
                chargeVO.setAccount(memUser.getUserAccount());
                chargeVO.setChangeType(39);
                chargeVO.setIsSilverBean(5);
                chargeVO.setDml(record.getBetAmount());
                chargeVO.setPrice(record.getBetAmount());
                chargeVO.setFlowType(2);
                chargeVO.setOrderNo(record.getId());
                chargeVO.setOpNote("第三方游戏记录");
                accountBalanceChangeService.publicAccountBalanceChange(chargeVO);
            }
        }
    }

}
