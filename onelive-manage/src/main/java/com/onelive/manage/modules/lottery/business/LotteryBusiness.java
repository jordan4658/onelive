package com.onelive.manage.modules.lottery.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.lottery.*;
import com.onelive.common.model.vo.lottery.*;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.lottery.*;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotteryBusiness {

    @Resource
    private LotteryCategoryService lotteryCategoryService;
    @Resource
    private LotteryService lotteryService;
    @Resource
    private LotteryLangService lotteryLangService;
    @Resource
    private LotteryPlayService lotteryPlayService;
    @Resource
    private LotteryPlayLangService lotteryPlayLangService;
    @Resource
    private LotteryPlaySettingService lotteryPlaySettingService;
    @Resource
    private LotteryPlaySettingLangService lotteryPlaySettingLangService;
    @Resource
    private LotteryPlayOddsService lotteryPlayOddsService;
    @Resource
    private LotteryPlayOddsLangService lotteryPlayOddsLangService;

    /**
     * 查询彩种列表
     *
     * @param req
     * @return
     */
    public PageInfo<LotteryVO> queryLotteryList(LotteryQueryReq req) {
        if (req == null) {
            req = new LotteryQueryReq();
        }
        List<LotteryCategory> lotteries = lotteryCategoryService.queryAllCategory("LOTTERY");
        //获取彩种大类名称
        Map<Integer, LotteryCategory> map = new HashMap<>();
        for (LotteryCategory cate : lotteries) {
            map.put(cate.getCategoryId(), cate);
        }

        QueryWrapper<Lottery> queryWrapper = new QueryWrapper<>();
        if (req.getLotteryId() != null) {
            queryWrapper.lambda().eq(Lottery::getLotteryId, req.getLotteryId());
        }
        if (req.getCateId() != null) {
            queryWrapper.lambda().eq(Lottery::getCategoryId, req.getCateId());
        }
        queryWrapper.lambda().eq(Lottery::getIsDelete,false);

        // PageInfo<Lottery>  pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> lotteryService.getBaseMapper().selectList(queryWrapper));
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<Lottery> lotteryList = lotteryService.list(queryWrapper);
        PageInfo<Lottery> pagePo = new PageInfo<>(lotteryList);

        List<LotteryVO> lotteryVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(lotteryList)) {
            Iterator<Lottery> iterator = lotteryList.iterator();
            while (iterator.hasNext()) {
                Lottery lottery = iterator.next();
                LotteryVO vo = new LotteryVO();
                BeanCopyUtil.copyProperties(lottery, vo);
                LotteryCategory category = map.get(lottery.getCategoryId());
                if (category != null) {
                    vo.setCategoryName(category.getName());
                }
//                 vo.setCreateTime(lottery.getCreateTime());
//                 vo.setCategoryId(lottery.getCategoryId());
                lotteryVOList.add(vo);
            }
        }

        PageInfo<LotteryVO> pageInfo = new PageInfo();
        BeanUtil.copyProperties(pagePo, pageInfo);
        pageInfo.setList(lotteryVOList);
        return pageInfo;
    }


    /**
     * 新增彩种
     *
     * @param req
     * @return
     */
    public void addLottery(LotteryAddReq req) {
        if (req.getCategoryId() == null || req.getLotteryId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //1、判断彩种编号是否重复添加
        Lottery pre = lotteryService.getLotteryByLotteryId(req.getLotteryId());
        if (pre != null) {
            throw new BusinessException(StatusCode.LOTTERY_EXISTS.getCode(), StatusCode.LOTTERY_EXISTS.getMsg());
        }

        //2、新增彩种
        Date now = new Date();
        Lottery entity = new Lottery();
        entity.setCategoryId(req.getCategoryId());
        entity.setLotteryId(req.getLotteryId());
        entity.setName(req.getName());
        entity.setStartlottoTimes(req.getStartlottoTimes());
        entity.setEndTime(req.getEndTime());
        entity.setIsWork(req.getIsWork());
        entity.setSort(req.getSort());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        lotteryService.save(entity);

        SystemRedisUtils.deleteLotteryCaches();
    }


    /**
     * 保存彩种
     *
     * @param req
     */
    @Transactional
    public void saveLottery(LotterySaveReq req) {
        Integer id = req.getId();
        if (id==null || req.getCategoryId() == null || req.getLotteryId() == null || req.getLangList() == null || req.getLangList().size() == 0) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        List<LotteryLangReq> langList = req.getLangList();

        String lotteryName = "";
        for (LotteryLangReq item : langList) {
            if (LangConstants.LANG_CN.equals(item.getLang())) {
                lotteryName = item.getLotteryName();
                break;
            }
        }

        if(StrUtil.isBlank(lotteryName)){
            throw  new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

//        Integer id = req.getId();
//        if (id == null) {
//            //添加
//            //1、判断彩种编号是否重复添加
//            Lottery pre = lotteryService.getLotteryByLotteryId(req.getLotteryId());
//            if (pre != null) {
//                throw new BusinessException(StatusCode.LOTTERY_EXISTS.getCode(), StatusCode.LOTTERY_EXISTS.getMsg());
//            }
//            //2、新增彩种
//            Lottery entity = new Lottery();
//            BeanCopyUtil.copyProperties(req, entity);
//            entity.setName(lotteryName);
//            lotteryService.save(entity);
//            id = entity.getId();
//        } else {
            //不能新增, 只能更新
            Lottery pre = lotteryService.getLotteryByLotteryId(req.getLotteryId());
            if (pre == null) {
                throw new BusinessException(StatusCode.LOTTERY_NOT_EXISTS.getCode(), StatusCode.LOTTERY_NOT_EXISTS.getMsg());
            }
            BeanCopyUtil.copyProperties(req, pre);
            pre.setName(lotteryName);
            lotteryService.updateById(pre);
     //   }

        //处理多语言
        List<LotteryLang> addList = new LinkedList<>();
        List<LotteryLang> updateList = new LinkedList<>();
        for (LotteryLangReq item : langList) {
            LotteryLang lang = new LotteryLang();
            BeanCopyUtil.copyProperties(item, lang);
            lang.setLotteryId(req.getLotteryId());
            if (item.getId() == null) {
                addList.add(lang);
            } else {
                updateList.add(lang);
            }
        }

        if (addList.size() > 0) {
            MysqlMethod.batchInsert(LotteryLang.class, addList);
        }

        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(LotteryLang.class, updateList);
        }

        SystemRedisUtils.deleteLotteryCaches();
    }

    /**
     * 删除对应的彩种
     *
     * @param req
     */
    public void delLottery(LotteryIdReq req) {
        if (req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        Lottery lottery = lotteryService.getById(req.getId());
        if (lottery == null) {
            throw new BusinessException(StatusCode.LOTTERY_NOT_EXISTS.getCode(), StatusCode.LOTTERY_NOT_EXISTS.getMsg());
        }
        lottery.setIsDelete(true);
        lottery.setUpdateTime(new Date());
        lotteryService.updateById(lottery);
        SystemRedisUtils.deleteLotteryCaches();
    }

    /**
     * 删除对应的玩法
     *
     * @param req
     */
    public void delLotteryPlay(LotteryIdReq req) {
        if (req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        QueryWrapper<LotteryPlay> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.lambda().eq(LotteryPlay::getId, req.getId());
        lotteryPlayService.getBaseMapper().delete(deleteWrapper);

        SystemRedisUtils.deleteLotteryCaches();
    }

    /**
     * 通过彩种获取玩法列表
     * @param req
     * @return
     */
    public List<LotteryPlayListVO> queryPlayList(LotteryPlayReq req) {
//        QueryWrapper<LotteryPlay> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(LotteryPlay::getLotteryId, req.getLotteryId());
//        queryWrapper.lambda().eq(LotteryPlay::getIsDelete, false);
        //玩法信息
        List<LotteryPlayExVo> list = lotteryPlayService.listLotteryPlayWithLang(req.getLotteryId(),LangConstants.LANG_CN);
        List<LotteryPlayListVO> voList = BeanCopyUtil.copyCollection(list, LotteryPlayListVO.class);
        QueryWrapper<LotteryPlaySetting> querySettingWrapper = new QueryWrapper<>();
        querySettingWrapper.lambda().eq(LotteryPlaySetting::getCateId, req.getCateId());
        querySettingWrapper.lambda().eq(LotteryPlaySetting::getIsDelete, false);
        List<LotteryPlaySetting> settingList = lotteryPlaySettingService.list(querySettingWrapper);
        Map<Integer, LotteryPlaySetting> settingMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(settingList)) {
            settingMap = settingList.stream().collect(Collectors.toMap(LotteryPlaySetting::getPlayId, Function.identity(), (key1, key2) -> key2));
        }

        for (LotteryPlayListVO vo : voList) {
            LotteryPlaySetting setting = settingMap.get(vo.getId());
            if(setting!=null) {
                vo.setSettingId(setting.getId());
            }
        }
        return voList;

    }

    /**
     * 保存彩票玩法信息
     *
     * @param req
     */
    @Transactional
    public void savePlay(LotteryPlaySaveReq req) {
        Integer id = req.getId();
        if (id == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        List<LotteryPlayLangReq> langList = req.getLangList();
        if (langList == null || langList.size() == 0) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        String playName = "";
        for (LotteryPlayLangReq item : langList) {
            if (LangConstants.LANG_CN.equals(item.getLang())) {
                playName = item.getPlayName();
                break;
            }
        }

        if(StrUtil.isBlank(playName)){
            throw  new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

//        Integer id = req.getId();
//        if (id == null) {
//            LotteryPlay play = new LotteryPlay();
//            BeanCopyUtil.copyProperties(req, play);
//            play.setName(playName);
//            lotteryPlayService.save(play);
//            id = play.getId();
//
//            //创建playSetting
//            LotteryPlaySetting setting = new LotteryPlaySetting();
//            setting.setPlayId(id);
//            setting.setCateId(play.getCategoryId());
//            setting.setPlayTagId(play.getPlayTagId());
//            lotteryPlaySettingService.save(setting);
//        } else {
        //不可新增, 只能更新
            LotteryPlay play = lotteryPlayService.getById(id);
            if (play == null) {
                throw new BusinessException(StatusCode.PARAM_ERROR);
            }
            BeanCopyUtil.copyProperties(req, play);
           // play.setName(playName);
            lotteryPlayService.updateById(play);
//        }

        List<LotteryPlayLang> addList = new LinkedList<>();
        List<LotteryPlayLang> updateList = new LinkedList<>();
        for (LotteryPlayLangReq item : langList) {
            LotteryPlayLang lang = new LotteryPlayLang();
            BeanCopyUtil.copyProperties(item, lang);
            lang.setPlayId(id);
            if (item.getId() == null) {
                addList.add(lang);
            } else {
                updateList.add(lang);
            }
        }
        if (addList.size() > 0) {
            MysqlMethod.batchInsert(LotteryPlayLang.class, addList);
        }
        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(LotteryPlayLang.class, updateList);
        }

        SystemRedisUtils.deleteLotteryCaches();
    }


    /**
     * 获取玩法规则信息
     *
     * @param req
     * @return
     */
    public LotteryPlaySettingVO getPlaySettingInfo(LotteryPlaySettingQueryReq req) {
        if (req == null || req.getId() == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryPlaySetting playSetting = lotteryPlaySettingService.getById(req.getId());
        if (playSetting == null) throw new BusinessException(StatusCode.PLAY_SETTING_NOT_EXISTS);
        LotteryPlaySettingVO vo = new LotteryPlaySettingVO();

        BeanCopyUtil.copyProperties(playSetting,vo);

        //查询多语言
        QueryWrapper<LotteryPlaySettingLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlaySettingLang::getSettingId,req.getId());
        List<LotteryPlaySettingLang> list = lotteryPlaySettingLangService.list(queryWrapper);

        if(CollectionUtils.isNotEmpty(list)) {
            List<LotteryPlaySettingLangVO> langList = BeanCopyUtil.copyCollection(list, LotteryPlaySettingLangVO.class);
            vo.setLangList(langList);
        }
        return vo;
    }


    /**
     * 保存玩法设置
     *
     * @param req
     */
    @Transactional
    public void savePlaySetting(LotteryPlaySettingSaveReq req) {

        Integer id = req.getId();
        List<LotteryPlaySettingLangReq> langList = req.getLangList();
        if (id == null || CollectionUtils.isEmpty(langList)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }


        LotteryPlaySetting playSetting = lotteryPlaySettingService.getById(req.getId());
        if (playSetting == null) {
            throw new BusinessException(StatusCode.PLAY_SETTING_NOT_EXISTS);
        }
        BeanCopyUtil.copyProperties(req, playSetting);
        for (LotteryPlaySettingLangReq item : langList) {
            if(LangConstants.LANG_CN.equals(item.getLang())){
                playSetting.setExample(item.getExample());
                playSetting.setExampleNum(item.getExampleNum());
                playSetting.setPlayRemark(item.getPlayRemark());
                playSetting.setPlayRemarkSx(item.getPlayRemarkSx());
                break;
            }
        }

        if(StrUtil.isBlank(playSetting.getExample())){
            throw  new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

        lotteryPlaySettingService.updateById(playSetting);


        List<LotteryPlaySettingLang> addList = new LinkedList<>();
        List<LotteryPlaySettingLang> updateList = new LinkedList<>();

        for (LotteryPlaySettingLangReq item : langList) {
            LotteryPlaySettingLang lang = new LotteryPlaySettingLang();
            BeanCopyUtil.copyProperties(item, lang);
            lang.setSettingId(id);
            if (item.getId() == null) {
                addList.add(lang);
            } else {
                updateList.add(lang);
            }
        }

        if (addList.size() > 0) {
            MysqlMethod.batchInsert(LotteryPlaySettingLang.class, addList);
        }
        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(LotteryPlaySettingLang.class, updateList);
        }

        SystemRedisUtils.deleteLotteryCaches();
    }


    /**
     * 彩种类型下拉-彩种名称下拉
     *
     * @return
     */
    public List<CategorySelectVO> getSelectLotteryList() {
        List<CategorySelectVO> voList = new ArrayList<>();
        QueryWrapper<LotteryCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryCategory::getIsDelete, false);
        List<LotteryCategory> list = lotteryCategoryService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            Iterator<LotteryCategory> iterator = list.iterator();
            while (iterator.hasNext()) {
                LotteryCategory bo = iterator.next();
                CategorySelectVO vo = new CategorySelectVO();
                BeanCopyUtil.copyProperties(bo, vo);
                List<Lottery> lotteryList = lotteryService.getLotteryByCategoryId(vo.getCategoryId());
                if (CollectionUtils.isNotEmpty(lotteryList)) {
                    List<CategorySelectChildVO> childList = new ArrayList<>();
                    Iterator<Lottery> iteratorLottery = lotteryList.iterator();
                    while (iteratorLottery.hasNext()) {
                        Lottery lottery = iteratorLottery.next();
                        CategorySelectChildVO lotteryVO = new CategorySelectChildVO();
                        BeanCopyUtil.copyProperties(lottery, lotteryVO);
                        childList.add(lotteryVO);
                    }
                    vo.setChildList(childList);
                }
                voList.add(vo);
            }
        }
        return voList;
    }


    /**
     * 根据ID查询彩种信息
     * @return
     */
    public LotteryEditVO getLotteryById(LotteryIdReq req) {
        Integer id = req.getId();
        if (id == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        Lottery lottery = lotteryService.getById(id);
        if (lottery == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryEditVO vo = new LotteryEditVO();
        BeanCopyUtil.copyProperties(lottery, vo);

        //查询多语言
        QueryWrapper<LotteryLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryLang::getLotteryId, lottery.getLotteryId());
        List<LotteryLang> list = lotteryLangService.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)) {
            List<LotteryLangVO> langList = BeanCopyUtil.copyCollection(list, LotteryLangVO.class);
            vo.setLangList(langList);
        }
        return vo;
    }

    /**
     * 保存赔率信息
     *
     * @param req
     */
    public void savePlayOdds(LotteryPlayOddsSaveReq req) {
        Integer id = req.getId();
        if (id == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        List<LotteryPlayOddsLangReq> langList = req.getLangList();
        if (CollectionUtils.isEmpty(langList)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        LotteryPlaySetting playSetting = lotteryPlaySettingService.getById(req.getSettingId());
        if (playSetting == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        String oddsName = "";

        for (LotteryPlayOddsLangReq item : langList) {
            if (LangConstants.LANG_CN.equals(item.getLang())) {
                oddsName = item.getOddsName();
                break;
            }
        }


        if(StrUtil.isBlank(oddsName)){
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }


        LotteryPlayOdds odds = new LotteryPlayOdds();
        BeanCopyUtil.copyProperties(req, odds);
       // odds.setName(oddsName);
//        if (id == null) {
//            lotteryPlayOddsService.save(odds);
//            id = odds.getId();
//        } else {
        //不可新增, 保能更新
            lotteryPlayOddsService.updateById(odds);
//        }

        List<LotteryPlayOddsLang> addList = new LinkedList<>();
        List<LotteryPlayOddsLang> updateList = new LinkedList<>();

        for (LotteryPlayOddsLangReq item : langList) {
            LotteryPlayOddsLang lang = new LotteryPlayOddsLang();
            BeanCopyUtil.copyProperties(item, lang);
            lang.setOddsId(id);
            if (item.getId() == null) {
                addList.add(lang);
            } else {
                updateList.add(lang);
            }
        }

        if (addList.size() > 0) {
            MysqlMethod.batchInsert(LotteryPlayOddsLang.class, addList);
        }
        if (updateList.size() > 0) {
            MysqlMethod.batchUpdate(LotteryPlayOddsLang.class, updateList);
        }
        SystemRedisUtils.deleteLotteryCaches();
    }

    /**
     * 删除赔率选项
     * @param req
     */
    public void delPlayOdds(LotteryIdReq req) {
        Integer id = req.getId();
        if (id == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        LotteryPlayOdds dbOdds = lotteryPlayOddsService.getById(id);
        if(dbOdds==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //加上了逻辑删除注解, 必须通过remove删除方法删除, update方法无效
        lotteryPlayOddsService.removeById(id);
        SystemRedisUtils.deleteLotteryCaches();
    }

    /**
     * 查询玩法信息
     * @param req
     * @return
     */
    public LotteryPlayVO getPlayInfo(LongIdReq req) {
        Long id = req.getId();
        if(id ==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        LotteryPlay play = lotteryPlayService.getById(id);
        if(play==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryPlayVO vo = new LotteryPlayVO();
        BeanCopyUtil.copyProperties(play,vo);

        //查询多语言
        QueryWrapper<LotteryPlayLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlayLang::getPlayId,id);
        List<LotteryPlayLang> list = lotteryPlayLangService.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            List<LotteryPlayLangVO> langList = BeanCopyUtil.copyCollection(list, LotteryPlayLangVO.class);
            vo.setLangList(langList);
        }
        return vo;
    }


    /**
     * 根据ID查询赔率信息
     * @param req
     * @return
     */
    public LotteryPlayOddsVO getPlayOdds(LotteryIdReq req) {
        Integer id = req.getId();
        if(id ==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        LotteryPlayOdds odds = lotteryPlayOddsService.getById(id);
        if (odds == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryPlayOddsVO vo = new LotteryPlayOddsVO();
        BeanCopyUtil.copyProperties(odds,vo);
        //查询多语言
        QueryWrapper<LotteryPlayOddsLang> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlayOddsLang::getOddsId,id);
        List<LotteryPlayOddsLang> list = lotteryPlayOddsLangService.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            List<LotteryPlayOddsLangVO> langList = BeanCopyUtil.copyCollection(list, LotteryPlayOddsLangVO.class);
            vo.setLangList(langList);
        }
        return vo;
    }

    /**
     * 查询赔率选项列表
     * @param req
     * @return
     */
    public List<LotteryPlayOddsListVO> queryPlayOddsList(LotteryPlayOddsReq req) {
        if(req==null || req.getSettingId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        QueryWrapper<LotteryPlayOdds> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlayOdds::getSettingId,req.getSettingId()).eq(LotteryPlayOdds::getIsDelete,false);
        List<LotteryPlayOdds> list = lotteryPlayOddsService.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            List<LotteryPlayOddsListVO> voList = BeanCopyUtil.copyCollection(list, LotteryPlayOddsListVO.class);
            return voList;
        }
        return new LinkedList<>();
    }

    /**
     * 查询彩票列表用于选择
     * @return
     */
    public List<LotterySelectVO> querySelectLotteryList() {
        QueryWrapper<Lottery> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getIsDelete,false);
        List<Lottery> lotteryList = lotteryService.list(queryWrapper);
        if(CollectionUtils.isEmpty(lotteryList)){
            return new LinkedList<>();
        }
        return BeanCopyUtil.copyCollection(lotteryList,LotterySelectVO.class);
    }
}
