package com.onelive.api.modules.live.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.onelive.common.model.vo.live.BuyProductVO;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.live.LiveAnchorRoomAdminService;
import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.live.LiveStudioLogService;
import com.onelive.api.service.live.RoomFastWordsService;
import com.onelive.api.service.live.RoomService;
import com.onelive.api.service.mem.MemUserAnchorService;
import com.onelive.api.service.mem.MemUserExpenseRecordService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.mem.MemWalletService;
import com.onelive.api.service.platform.LiveFloatLangService;
import com.onelive.api.service.platform.LiveGiftLangService;
import com.onelive.api.service.platform.LiveGiftLogService;
import com.onelive.api.service.platform.LiveGiftService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.client.WebSocketFeignClient;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.live.RoomFastWordsDto;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.dto.platform.GiftGivingDto;
import com.onelive.common.model.dto.platform.KickingDto;
import com.onelive.common.model.dto.platform.LiveFloatForIndexDto;
import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.model.dto.platform.LiveGiftLogDto;
import com.onelive.common.model.dto.platform.ProductForRoomDto;
import com.onelive.common.model.dto.platform.SendBarrageDto;
import com.onelive.common.model.req.live.BannedReq;
import com.onelive.common.model.req.live.GiftGivingComboEndReq;
import com.onelive.common.model.req.live.GiftGivingReq;
import com.onelive.common.model.req.live.LiveStudioNumReq;
import com.onelive.common.model.req.platform.GiftgCheckDto;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.webSocket.SendMsgVO;
import com.onelive.common.model.vo.webSocket.UserDetailForBarrageVO;
import com.onelive.common.model.vo.webSocket.UserDetailForGiftVO;
import com.onelive.common.mybatis.entity.LiveAnchorRoomAdmin;
import com.onelive.common.mybatis.entity.LiveGift;
import com.onelive.common.mybatis.entity.LiveGiftLog;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.LiveStudioLog;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.DateUtils;
import com.onelive.common.utils.pay.SnowflakeIdWorker;
import com.onelive.common.utils.sys.SystemUtil;
import com.onelive.common.utils.upload.AWSS3Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RoomBusiness {

    @Resource
    private WebSocketFeignClient wbeSocketFeignClient;

    @Resource
    private LiveGiftService liveGiftService;

    @Resource
    private LiveGiftLangService liveGiftLangService;

    @Resource
    private LiveGiftLogService liveGiftLogService;

    @Resource
    private MemUserService memUserService;

    @Resource
    private MemWalletService memWalletService;

    @Resource
    private MemUserAnchorService memUserAnchorService;

    @Resource
    private LiveStudioListService liveStudioListService;

    @Resource
    private LiveStudioLogService liveStudioLogService;

    @Resource
    private MemUserExpenseRecordService memUserExpenseRecordService;

    @Resource
    private LiveAnchorRoomAdminService liveAnchorRoomAdminService;

    @Resource
    private RoomFastWordsService roomFastWordsService;

    @Resource
    private SysParameterService sysParameterService;

    @Resource
    private RoomService roomService;

    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private LiveFloatLangService liveFloatLangService;

    /**
     * 简单查询礼物配置
     *
     * @return
     */
    public List<LiveGiftForIndexDto> getGiftList() {
        String lang = StringUtils.isEmpty(LoginInfoUtil.getLang()) ? "zh_CN" : LoginInfoUtil.getLang();
        List<LiveGiftForIndexDto> list = liveGiftService.getLiveRoomList(LoginInfoUtil.getCountryCode(), lang);
        return list;
    }

    /**
     * 直播间内悬浮窗
     *
     * @return
     */
    public List<LiveFloatForIndexDto> getFloatList() {
        return liveFloatLangService.getFloatList(LoginInfoUtil.getCountryCode(), LoginInfoUtil.getLang());
    }

    /**
     * 送礼 礼物赠送
     *
     * @param giftGivingDto
     * @return
     */
    @Transactional
    public ResultInfo<GiftGivingDto> giftGiving(GiftGivingReq giftGivingDto) {
        if (giftGivingDto.getHostId() == null) {
            return ResultInfo.getInstance(StatusCode.GIFT_HOSTID_ISNULL);
        }
        if (giftGivingDto.getGiftId() == null) {
            return ResultInfo.getInstance(StatusCode.GIFT_GIFTID_ISNULL);
        }
        // 1. 查询礼物
        LiveGiftDto liveGift = liveGiftService.getByIdAndLang(giftGivingDto.getGiftId(), LoginInfoUtil.getLang());
        if (liveGift == null) {
            return ResultInfo.getInstance(StatusCode.UNUSE_GIFT_STATUS);
        }

        // 当前直播间状态，如果已关闭，不可送礼
        // 当前直播间信息
        LiveStudioLog liveStudioLog = liveStudioLogService.selectLastByUserId(giftGivingDto.getHostId());

        if (liveStudioLog == null) {
            log.info("主播账号ID：" + giftGivingDto.getHostId());
            throw new BusinessException("当前主播开播信息不存在！");
        }
        String studioNum = liveStudioLog.getStudioNum();
        // 直播间开播缓存
        Object isExist = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.anchor_studioNum, studioNum);
        // 正在关播中
//		RLock liveLock = redissonClient.getLock(RedisKeys.SYNC_LIVE_STUDIO_CLOSE + studioNum);
//        if (liveLock.isLocked() || isExist == null) {
//        	return ResultInfo.getInstance(StatusCode.STUDIO_ALREADY_CLOSE);
//        }

        String account = LoginInfoUtil.getUserAccount();
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.SYNC_LIVE_GIFT_GIVING + account);
        try {
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            if (bool) {
                Long userId = LoginInfoUtil.getUserId();
                // 3.查询赠送人余额
                QueryWrapper<MemWallet> queryWrapperWallet = new QueryWrapper<>();
                queryWrapperWallet.lambda().eq(MemWallet::getUserId, userId);
                queryWrapperWallet.lambda().eq(MemWallet::getWalletType, 1);
                BigDecimal balance = memWalletService.getOne(queryWrapperWallet).getSilverBean();

                // 4.防止恶意请求,比较当前当前银豆余额 是否大于 礼物数量 * 礼物价格
                BigDecimal priceTotal = liveGift.getPrice().multiply(new BigDecimal(giftGivingDto.getGiftNumber()));
                // 如果当前礼物总额大于钱包银豆余额
                if (priceTotal.compareTo(balance) == 1) {
                    return ResultInfo.getInstance(StatusCode.CHANGE_BALANCE_LACK);
                }

                // 礼物订单id
                String generateShortId = SnowflakeIdWorker.generateShortId();
                // 5. 用户扣除钱包银豆余额
                MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
                memAccountChangeVO.setOrderNo(generateShortId);
                memAccountChangeVO.setAccount(LoginInfoUtil.getUserAccount());
                memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_1.getCode());
                memAccountChangeVO.setSilverBeanPrice(priceTotal.multiply(new BigDecimal("-1")));
                memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE25.getPayTypeCode());
                memAccountChangeVO.setOpNote(giftGivingDto.getGiftId().toString());
                BalanceChangeDTO flagVo = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
                if (!flagVo.getFlag()) {
                    log.error("用户账号：{} 打赏礼物主播账号：{} 银豆账变失败！", userId, giftGivingDto.getHostId());
                    throw new BusinessException("用户账号：" + LoginInfoUtil.getUserAccount() + " 打赏礼物主播账号："
                            + giftGivingDto.getHostId() + " 银豆账变失败！");
                }
                // 6.更新主播和用户---等级积分信息和账户信息 7. 添加消费记录
                memUserExpenseRecordService.addExpenseRecord(LoginInfoUtil.getUserId(), priceTotal, 1,
                        liveStudioLog.getStudioNum());
                Integer giftComboNumb = 1;// 礼物连击次数
                if (liveGift.getIsDoubleHit()) {
                    // 统计礼物连接次数
                    Map<String, Object> parameterMap = new HashMap<String, Object>(8);
                    parameterMap.put("hostId", giftGivingDto.getHostId());
                    parameterMap.put("rowNum", null);
                    parameterMap.put("giftId", giftGivingDto.getGiftId());
                    parameterMap.put("givingId", userId);
                    parameterMap.put("giftComboId", giftGivingDto.getGiftComboId());
                    // 统计连击次数累加
                    Integer giftNumberMap = liveGiftLogService.getGiftComboTotal(parameterMap);
                    if (giftNumberMap != null && giftNumberMap != 0) {
                        giftComboNumb = giftNumberMap + giftComboNumb;
                    }
                }
                // 8. 添加礼物赠送日志
                LiveGiftLog liveGiftLog = new LiveGiftLog();
                liveGiftLog.setHostId(giftGivingDto.getHostId());
                liveGiftLog.setGivingId(userId);
                liveGiftLog.setGiftId(giftGivingDto.getGiftId());
                liveGiftLog.setGiftNumber(giftGivingDto.getGiftNumber());
                liveGiftLog.setGiftPrice(liveGift.getPrice());
                liveGiftLog.setGiftComboId(giftGivingDto.getGiftComboId());
                liveGiftLog.setStudioLogId(liveStudioLog.getLogId());
                liveGiftLog.setGivingLocalTime(SystemUtil.getLangTime());
                liveGiftLog.setGoldChangeNo(flagVo.getChangeOrderNo());
                liveGiftLog.setGiftLogNo(generateShortId);
                liveGiftLogService.save(liveGiftLog);
                // 异步消息通知 以及缓存处理
                asynGiftMsg(liveStudioLog.getStudioNum(), giftGivingDto.getHostId(), priceTotal, userId, giftComboNumb,
                        giftGivingDto.getGiftComboId(), liveGift);
                return ResultInfo.ok(new GiftGivingDto(giftGivingDto.getGiftComboId(), balance.subtract(priceTotal)));
            } else {
                log.error("giftGiving 用户账号:{}送礼物没拿到锁, 送礼失败 ", account);
                return ResultInfo.getInstance(StatusCode.PROCESSING);
            }
        } catch (Exception e) {
            log.error("giftGiving 用户账号:" + account + "送礼物异常, 送礼失败 ", e);
            return ResultInfo.getInstance(StatusCode.LIVE_GIFT_GIVING_FAIL);
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("giftGiving 用户账号:{} 送礼结束，释放锁 ", account);
        }
    }


    /**
     * 礼物赠送异步消息通知， 以及缓存处理
     *
     * @param studioNum
     * @param hostId
     * @param priceTotal
     * @param userId
     * @param giftComboNumbFinal
     * @param giftComboId
     * @param liveGift
     */
    private void asynGiftMsg(String studioNum, Long hostId, BigDecimal priceTotal, Long userId,
                             Integer giftComboNumbFinal, String giftComboId, LiveGiftDto liveGift) {
        taskExecutor.execute(() -> {
            // 累计本场直播金额
            Object giftTotalObject = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.live_StudioLog_gift, studioNum);
            BigDecimal giftTotal = giftTotalObject == null ? priceTotal
                    : new BigDecimal(giftTotalObject.toString()).add(priceTotal);
            ApiBusinessRedisUtils.hset(WebSocketRedisKeys.live_StudioLog_gift, studioNum, giftTotal);
            /** 缓存数据操作 */
            // 更新直播间热度
            // 已有的火力值
            Object studioNum_Heat = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, studioNum);
            BigDecimal studioNumHeat = studioNum_Heat == null ? new BigDecimal("0")
                    : new BigDecimal(studioNum_Heat.toString());
            // 加上礼物的金额 * 10
            studioNumHeat = priceTotal.multiply(new BigDecimal(10)).add(studioNumHeat).setScale(0,
                    BigDecimal.ROUND_DOWN);
            ApiBusinessRedisUtils.hSet(WebSocketRedisKeys.studioNum_Heat, studioNum.toString(),
                    studioNumHeat.intValue());
            // TODO 查询 排行榜 缓存，比较大小，判断是否进行数据重置
            // 消息通告数据组装
            try {
                // 返回的数据对象
                LiveUserDetailVO userDetailInfo = memUserService.getUserDetailForList(userId);
                UserDetailForGiftVO userDetailForGiftVO = new UserDetailForGiftVO();
                BeanUtils.copyProperties(userDetailInfo, userDetailForGiftVO);
                liveGift.setGiftNumber(giftComboNumbFinal);
                userDetailForGiftVO.setLiveGift(liveGift);
                userDetailForGiftVO.getLiveGift().setGiftComboId(giftComboId);

                SendMsgVO roomMsgVO = new SendMsgVO();
                roomMsgVO.setContent(JSONObject.toJSONString(userDetailForGiftVO));
                roomMsgVO.setOperatorType(6);
                roomMsgVO.setTargetId(studioNum);
                wbeSocketFeignClient.sendRoomMsg(roomMsgVO);
                // 世界礼物,所有直播间进行通知
                if (liveGift.getGiftType() == 1) {
                    // 查询主播的开播地址 昵称 房间号
                    LiveAnchorDetailVO liveAnchorDetailVO = memUserAnchorService.selectLiveAnchorDetail(hostId);
                    userDetailForGiftVO.setAnchorDetail(liveAnchorDetailVO);
                    userDetailForGiftVO.setAvatar(AWSS3Util.getAbsoluteUrl(userDetailForGiftVO.getAvatar()));
                    SendMsgVO wordMsgVO = new SendMsgVO();
                    wordMsgVO.setContent(JSONObject.toJSONString(userDetailForGiftVO));
                    wordMsgVO.setOperatorType(6);
                    wordMsgVO.setTargetId(studioNum);
                    wbeSocketFeignClient.sendRoomMsg(wordMsgVO);
                }
                // 返回的数据对象
                if (!liveGift.getIsDoubleHit()) {
                    //非连击礼物发送结束连击消息
                    BeanUtils.copyProperties(userDetailInfo, userDetailForGiftVO);
                    liveGift.setGiftNumber(1);
                    userDetailForGiftVO.setLiveGift(liveGift);
                    userDetailForGiftVO.setGiftTotal(new BigDecimal(1));
                    userDetailForGiftVO.getLiveGift().setGiftComboId(giftComboId);
                    SendMsgVO roomMsgVO2 = new SendMsgVO();
                    roomMsgVO2.setContent(JSONObject.toJSONString(userDetailForGiftVO));
                    roomMsgVO2.setOperatorType(11);
                    roomMsgVO2.setTargetId(studioNum);
                    log.info("非连击礼物---发送结束连击礼物消息---：{}", JSONObject.toJSONString(roomMsgVO2));
                    wbeSocketFeignClient.sendRoomMsg(roomMsgVO2);
                }
                // 通知主播更新本场直播收礼金额
                SendMsgVO megToAnchor = new SendMsgVO();
                megToAnchor.setContent(giftTotal.toString());
                megToAnchor.setOperatorType(12);
                megToAnchor.setTargetId(hostId.toString());
                wbeSocketFeignClient.sendSingleMsg(megToAnchor);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("推送礼物消息失败!");
            }
        });
    }

    /**
     * 用户发送弹幕 1.扣钱 2.升级
     *
     * @param sendBarrageDto
     * @return
     */
    @Transactional
    public ResultInfo<GiftGivingDto> sendBarrage(SendBarrageDto sendBarrageDto) {
        if (StringUtils.isEmpty(sendBarrageDto.getBarrageContent())) {
            return ResultInfo.getInstance(StatusCode.BARRAGE_CONTENT_ISNULL);
        }
        if (sendBarrageDto.getHostId() == null) {
            return ResultInfo.getInstance(StatusCode.GIFT_HOSTID_ISNULL);
        }
        // 游客不可以送礼
        if (LoginInfoUtil.getUserType() == -1) {
            throw new BusinessException(StatusCode.LIVE_GIFT_NOT_TOURIST);
        }

        // 查询最新的直播日志 根据user_id
        LiveStudioLog liveStudioLog = liveStudioLogService.selectLastByUserId(sendBarrageDto.getHostId());

        String studioNum = liveStudioLog.getStudioNum();
        // 直播间缓存标记
        Object isExist = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.anchor_studioNum, studioNum);
        // 正在关播中
        RLock liveLock = redissonClient.getLock(RedisKeys.SYNC_LIVE_STUDIO_CLOSE + studioNum);
        if (liveLock.isLocked() || isExist == null) {
            return ResultInfo.getInstance(StatusCode.STUDIO_ALREADY_CLOSE);
        }

        // 用户详情
        LiveUserDetailVO userDetailInfo = memUserService.getUserDetailForList(LoginInfoUtil.getUserId());
        String barrageLevel = sysParameterService.getByCode(SysParameterConstants.BARRAGE_RESTRICTIONS).getParamValue();

        // 直播间公共配置
        if (userDetailInfo.getLevel() < Integer.parseInt(barrageLevel)) {
            return ResultInfo.getInstance(StatusCode.BARRAGE_LEVEL_INSUFFICIENT);
        }
        // 1. 查询弹幕价格
        LiveGift barrage = liveGiftService.getBarrage();
        BigDecimal barragePrice = barrage.getPrice();
        String userAccount = userDetailInfo.getUserAccount();

        // 弹幕锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.SYNC_LIVE_GIFT_BARRAGE + userAccount);

        try {
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            if (bool) {
                // 3.查询赠送人银豆余额
                QueryWrapper<MemWallet> queryWrapperWallet = new QueryWrapper<>();
                queryWrapperWallet.lambda().eq(MemWallet::getUserId, LoginInfoUtil.getUserId());
                queryWrapperWallet.lambda().eq(MemWallet::getWalletType, 1);
                BigDecimal balance = memWalletService.getOne(queryWrapperWallet).getSilverBean();

                // 4.如果当前弹幕价格总额大于钱包余额
                if (barragePrice.compareTo(balance) == 1) {
                    return ResultInfo.getInstance(StatusCode.CHANGE_BALANCE_LACK);
                }
                // 礼物订单id
                String generateShortId = SnowflakeIdWorker.generateShortId();

                // 5. 扣除钱包银豆余额
                MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
                memAccountChangeVO.setOrderNo(generateShortId);
                memAccountChangeVO.setAccount(LoginInfoUtil.getUserAccount());
                memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_1.getCode());
                memAccountChangeVO.setSilverBeanPrice(barragePrice.multiply(new BigDecimal("-1")));
                memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE26.getPayTypeCode());
                memAccountChangeVO.setOpNote(barrage.getGiftId().toString());
                BalanceChangeDTO flagVo = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
                if (!flagVo.getFlag()) {
                    log.error("用户账号：{} 发送弹幕 直播间号：{} 银豆账变失败！", userAccount, studioNum);
                    throw new BusinessException("用户账号：" + userAccount + " 发送弹幕 直播间号：" + studioNum + " 银豆账变失败！");
                }

                // 6.更新主播和用户---等级积分信息和账户信息 7. 添加消费记录
                memUserExpenseRecordService.addExpenseRecord(LoginInfoUtil.getUserId(), barragePrice, 1, studioNum);

                // 8. 添加礼物赠送日志
                LiveGiftLog liveGiftLog = new LiveGiftLog();
                liveGiftLog.setHostId(sendBarrageDto.getHostId());
                liveGiftLog.setGivingId(LoginInfoUtil.getUserId());
                liveGiftLog.setGiftId(barrage.getGiftId());
                liveGiftLog.setGiftNumber(1);
                liveGiftLog.setStudioLogId(liveStudioLog.getLogId());
                liveGiftLog.setGiftPrice(barragePrice);
                liveGiftLog.setGoldChangeNo(flagVo.getChangeOrderNo());
                liveGiftLog.setGivingLocalTime(SystemUtil.getLangTime());
                liveGiftLog.setGiftLogNo(generateShortId);
                liveGiftLogService.save(liveGiftLog);
                asynBarrageMsg(barragePrice, studioNum, userDetailInfo, sendBarrageDto);
                GiftGivingDto giftGivingDto = new GiftGivingDto();
                giftGivingDto.setBalance(balance);
                return ResultInfo.ok(giftGivingDto);
            } else {
                log.error("sendBarrage 用户账号:{} 发送弹幕没拿到锁, 弹幕发送失败 ", userAccount);
                return ResultInfo.getInstance(StatusCode.PROCESSING);
            }
        } catch (Exception e) {
            log.error("sendBarrage 用户账号" + userAccount + "发送弹幕异常, 发送弹幕失败", e);
            return ResultInfo.getInstance(StatusCode.LIVE_GIFT_BARRAGE_FAIL);
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("sendBarrage 用户账号:{}  发送弹幕结束，释放锁 ", userAccount);
        }
    }

    /**
     * 异步推送 弹幕消息，以及相关缓存修改
     *
     * @param barragePrice
     * @param studioNum
     * @param userDetailInfo
     * @param sendBarrageDto
     */
    private void asynBarrageMsg(BigDecimal barragePrice, String studioNum, LiveUserDetailVO userDetailInfo, SendBarrageDto sendBarrageDto) {
        taskExecutor.execute(() -> {
            // 记录本场礼物金额到缓存
            Object giftTotalObject = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.live_StudioLog_gift, studioNum);
            BigDecimal giftTotal = giftTotalObject == null ? barragePrice : new BigDecimal(giftTotalObject.toString()).add(barragePrice);
            ApiBusinessRedisUtils.hset(WebSocketRedisKeys.live_StudioLog_gift, studioNum, giftTotal);

            // 更新直播间热度
            // 已有的热度
            Object studioNum_Heat = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat,
                    studioNum);
            BigDecimal studioNumHeat = studioNum_Heat == null ? new BigDecimal("0") : new BigDecimal(studioNum_Heat.toString());
            // 加上弹幕金额 * 10
            studioNumHeat = barragePrice.multiply(new BigDecimal(10)).add(studioNumHeat).setScale(0, BigDecimal.ROUND_DOWN);
            ApiBusinessRedisUtils.hSet(WebSocketRedisKeys.studioNum_Heat, studioNum, studioNumHeat.intValue());

            // TODO 查询 排行榜 缓存，比较大小，判断是否进行数据重置

            // 弹幕消息通告数据组装
            try {
                // 返回的数据对象
                UserDetailForBarrageVO userDetailForBarrageVO = new UserDetailForBarrageVO();
                BeanUtils.copyProperties(userDetailInfo, userDetailForBarrageVO);
                userDetailForBarrageVO.setBarrage(sendBarrageDto.getBarrageContent());
                SendMsgVO wordMsgVO = new SendMsgVO();
                wordMsgVO.setContent(JSONObject.toJSONString(userDetailForBarrageVO));
                wordMsgVO.setOperatorType(7);
                wordMsgVO.setTargetId(studioNum);
                wbeSocketFeignClient.sendRoomMsg(wordMsgVO);

                log.info("websocketServer" + userDetailInfo.getNickName() + "给主播发送了弹幕:"
                        + sendBarrageDto.getBarrageContent());

                // 通知主播更新本场直播收礼金额
                SendMsgVO megToAnchor = new SendMsgVO();
                megToAnchor.setContent(giftTotal.toString());
                megToAnchor.setOperatorType(12);
                megToAnchor.setTargetId(sendBarrageDto.getHostId().toString());
                wbeSocketFeignClient.sendSingleMsg(megToAnchor);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("推送弹幕消息失败!");
            }
        });
    }

    /**
     * 管理/主播踢人
     *
     * @param kickingDto
     * @return
     */
    public ResultInfo<Boolean> kicking(KickingDto kickingDto) {
        if (StringUtils.isEmpty(kickingDto.getStudioNum())) {
            return ResultInfo.getInstance(StatusCode.LIVE_STUDIONUM_ISNULL);
        }
        if (kickingDto.getBUserId() == null) {
            return ResultInfo.getInstance(StatusCode.BUSERID_CANT_NULL);
        }
        if (kickingDto.getBUserId().equals(LoginInfoUtil.getUserId())) {
            return ResultInfo.getInstance(StatusCode.KICKING_CANT_SELF);
        }

        // 1.查询当前用户在直播间的权限
        // 当前直播间信息
        LiveStudioList liveStudioList = liveStudioListService.getByRoomNum(kickingDto.getStudioNum());
        // 被踢人是否是当前主播
        if (liveStudioList.getUserId().equals(kickingDto.getBUserId())) {
            return ResultInfo.getInstance(StatusCode.KICKING_CANT_ANCHOR);
        }
        // 查询被踢人是否是管理员
        MemUser memUser = memUserService.queryById(kickingDto.getBUserId());
        if (memUser.getIsSuperLiveManage()) {
            return ResultInfo.getInstance(StatusCode.KICKING_CANT_SUPERAMDIN);
        }

        // 如果当前不是主播本人踢人，并且不是超级管理 ，校验踢人者的权限
        if (!liveStudioList.getUserId().equals(LoginInfoUtil.getUserId()) && !memUser.getIsSuperLiveManage()) {
            // 查询当前直播间的管理员
            QueryWrapper<LiveAnchorRoomAdmin> liveAnchorRoomAdminQueryWrapper = new QueryWrapper<>();
            liveAnchorRoomAdminQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAnchorId, liveStudioList.getUserId()); // 主播的userid
            liveAnchorRoomAdminQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAdminId, LoginInfoUtil.getUserId()); // 当前管理员id
            LiveAnchorRoomAdmin liveAnchorRoomAdmin = liveAnchorRoomAdminService
                    .getOne(liveAnchorRoomAdminQueryWrapper);
            // 踢人者不是房间管理员
            if (liveAnchorRoomAdmin == null) {
                return ResultInfo.getInstance(StatusCode.NOT_ROOM_ADMIN_NULL);
            }
            // 2.查询被踢人是否是管理员
            QueryWrapper<LiveAnchorRoomAdmin> liveAnchorRoombUserIdQueryWrapper = new QueryWrapper<>();
            liveAnchorRoombUserIdQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAnchorId, liveStudioList.getUserId());
            liveAnchorRoombUserIdQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAdminId, kickingDto.getBUserId());
            LiveAnchorRoomAdmin liveAnchorRoomBUser = liveAnchorRoomAdminService
                    .getOne(liveAnchorRoombUserIdQueryWrapper);
            if (liveAnchorRoomBUser != null) {
                return ResultInfo.getInstance(StatusCode.NOT_ROOM_ADMIN_KICKING);
            }
        }

        // 3.记录被踢用户到缓存,下次再点击进入房间查询该缓存
        Long kickingTime = 60 * 12L; // 踢出后不能再进入的时间
        Long endTime = System.currentTimeMillis() + kickingTime * 1000;// 结束时间,方便后期提示用
        ApiBusinessRedisUtils.hSet(WebSocketRedisKeys.kicking_room_key + kickingDto.getStudioNum(),
                kickingDto.getBUserId().toString(), endTime, kickingTime);// 将b被踢人信息缓存在redis中
        // 用户列表
        ApiBusinessRedisUtils.hdel(WebSocketRedisKeys.user_list + kickingDto.getStudioNum(), kickingDto.getBUserId());

        // 4.通知用户被踢
        try {
            // 查询踢人者信息
            MemUser kickingUser = memUserService.queryById(LoginInfoUtil.getUserId());
            SendMsgVO singleMsgVo = new SendMsgVO();
            singleMsgVo.setContent("您已被" + kickingUser.getNickName() + "踢出直播间！");
            singleMsgVo.setOperatorType(2);
            singleMsgVo.setTargetId(kickingDto.getBUserId().toString());
            wbeSocketFeignClient.sendSingleMsg(singleMsgVo);
            wbeSocketFeignClient.removeUser(kickingDto.getBUserId());

        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送被踢消息失败!");
        }

        return ResultInfo.ok();
    }

    /**
     * 管理/主播禁言观众
     *
     * @return
     */
    public ResultInfo<Boolean> banned(BannedReq bannedReq) {
        if (StringUtils.isEmpty(bannedReq.getStudioNum())) {
            return ResultInfo.getInstance(StatusCode.LIVE_STUDIONUM_ISNULL);
        }
        if (bannedReq.getBUserId() == null) {
            return ResultInfo.getInstance(StatusCode.BANNED_BUSERID_CANT_NULL);
        }

        // 1.查询当前用户在直播间的权限
        // 当前直播间信息
        LiveStudioList liveStudioList = liveStudioListService.getByRoomNum(bannedReq.getStudioNum());

        // 如果当前不是主播本人禁言
        if (!liveStudioList.getUserId().equals(LoginInfoUtil.getUserId())) {
            QueryWrapper<LiveAnchorRoomAdmin> liveAnchorRoomAdminQueryWrapper = new QueryWrapper<>();
            liveAnchorRoomAdminQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAnchorId, liveStudioList.getUserId()); // 主播的userid
            liveAnchorRoomAdminQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAdminId, LoginInfoUtil.getUserId()); // 当前管理员id
            LiveAnchorRoomAdmin liveAnchorRoomAdmin = liveAnchorRoomAdminService
                    .getOne(liveAnchorRoomAdminQueryWrapper);
            // 在直播间管理表中没有当前用户
            if (liveAnchorRoomAdmin == null) {
                return ResultInfo.getInstance(StatusCode.NOT_ROOM_ADMIN_NULL);
            }
            // 2.查询被禁言人是否是管理员
            QueryWrapper<LiveAnchorRoomAdmin> liveAnchorRoombUserIdQueryWrapper = new QueryWrapper<>();
            liveAnchorRoombUserIdQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAnchorId, liveStudioList.getUserId());
            liveAnchorRoombUserIdQueryWrapper.lambda().eq(LiveAnchorRoomAdmin::getAdminId, bannedReq.getBUserId());
            LiveAnchorRoomAdmin liveAnchorRoomBUser = liveAnchorRoomAdminService
                    .getOne(liveAnchorRoombUserIdQueryWrapper);
            if (liveAnchorRoomBUser != null) {
                return ResultInfo.getInstance(StatusCode.NOT_ROOM_ADMIN_BANNED);
            }
        }

        // 3.记录被禁言用户到缓存,下次再发言查询该缓存
        Integer bannedTime = bannedReq.getBannedTime(); // 禁言时间，秒
        Long endTime = System.currentTimeMillis() + bannedTime * 1000;// 结束时间,方便后期提示用
        ApiBusinessRedisUtils.hSet(WebSocketRedisKeys.banned_room_key + bannedReq.getStudioNum(),
                bannedReq.getBUserId().toString(), endTime, bannedTime);// 将b被踢人信息缓存在redis中
        // 4.通知用户被禁言
        try {
            // 查询禁言人信息
            MemUser bannedUser = memUserService.queryById(LoginInfoUtil.getUserId());

            SendMsgVO singleMsgVo = new SendMsgVO();
            singleMsgVo.setContent("您已被" + bannedUser.getNickName() + "禁言！");
            singleMsgVo.setOperatorType(3);
            singleMsgVo.setTargetId(bannedReq.getBUserId().toString());
            wbeSocketFeignClient.sendSingleMsg(singleMsgVo);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送被禁言消息失败!");
        }

        return ResultInfo.ok();
    }

    /**
     * 校验当前用户是否可以发言， true: 可以发言
     *
     * @return
     */
    public ResultInfo<Boolean> checkBanned(LiveStudioNumReq liveStudioNumReq) {
        Object banned = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.banned_room_key + liveStudioNumReq.getStudioNum(),
                LoginInfoUtil.getUserId().toString());
        return ResultInfo.ok(banned == null);
    }

    /**
     * 校验当前用户是否可以发言， true: 可以发言
     *
     * @param liveStudioNumReq
     * @return
     */
    public ResultInfo<Boolean> checKicking(LiveStudioNumReq liveStudioNumReq) {
        Object kicking = ApiBusinessRedisUtils.hGet(
                WebSocketRedisKeys.kicking_room_key + liveStudioNumReq.getStudioNum(),
                LoginInfoUtil.getUserId().toString());
        return ResultInfo.ok(kicking == null);
    }

    /**
     * 礼物校验接口：当前礼物是否可赠送，不可赠送需刷新礼物列表
     *
     * @param giftgCheckDto
     * @return
     */
    public ResultInfo<Boolean> checkGift(GiftgCheckDto giftgCheckDto) {
        LiveGift byId = liveGiftService.getById(giftgCheckDto.getGiftId());
        return ResultInfo.ok(byId.getStatus());
    }

    /**
     * 根据请求头lang 返回快捷语
     *
     * @return
     */
    public List<RoomFastWordsDto> fastWords() {
        return BeanCopyUtil.copyCollection(roomFastWordsService.getAll(),
                RoomFastWordsDto.class);
    }

    /**
     * 查询直播间在线观众50个
     *
     * @param liveStudioNumReq
     * @return
     */
    public List<LiveUserDetailVO> onlineUsers(LiveStudioNumReq liveStudioNumReq) {
        return roomService.onlineUsers(liveStudioNumReq.getStudioNum());
    }

    /**
     * 查询直播间在线观众数量
     *
     * @return
     */
    public Integer onlineUsersCount(String studioNum) {
        return liveStudioListService.onlineUsersCount(studioNum);
    }

    public Boolean giftGivingComboEnd(GiftGivingComboEndReq giftGivingComboEndReq) {
        if (giftGivingComboEndReq.getGiftId() == null) {
            throw new BusinessException("礼物ID为空！");
        }
        if (StringUtils.isEmpty(giftGivingComboEndReq.getStudioNum())) {
            throw new BusinessException("直播间ID为空！");
        }
        if (giftGivingComboEndReq.getHostId() == null) {
            throw new BusinessException("主播ID为空！");
        }
        if (giftGivingComboEndReq.getGiftComboId() == null) {
            throw new BusinessException("礼物连击Id！");
        }
        try {
            // 1. 查询礼物
            LiveGiftDto liveGift = liveGiftService.getByIdAndLang(giftGivingComboEndReq.getGiftId(), LoginInfoUtil.getLang());
            Integer giftComboNumb = 1;//礼物连击次数
            if (liveGift.getIsDoubleHit()) {
                //统计礼物连接次数
                Map<String, Object> parameterMap = new HashMap<String, Object>();
                parameterMap.put("hostId", giftGivingComboEndReq.getHostId());
                parameterMap.put("rowNum", null);
                parameterMap.put("giftId", giftGivingComboEndReq.getGiftId());
                parameterMap.put("givingId", LoginInfoUtil.getUserId());
                parameterMap.put("giftComboId", giftGivingComboEndReq.getGiftComboId());
                // 统计连击次数累加
                Integer giftNumberMap = liveGiftLogService.getGiftComboTotal(parameterMap);
                if (giftNumberMap != null && giftNumberMap != 0) {
                    giftComboNumb = giftNumberMap;
                }
            }
            // 返回的数据对象
            LiveUserDetailVO userDetailInfo = memUserService.getUserDetailForList(LoginInfoUtil.getUserId());
            UserDetailForGiftVO userDetailForGiftVO = new UserDetailForGiftVO();
            BeanUtils.copyProperties(userDetailInfo, userDetailForGiftVO);
            liveGift.setGiftNumber(giftComboNumb);
            userDetailForGiftVO.setLiveGift(liveGift);
            userDetailForGiftVO.setGiftTotal(new BigDecimal(giftComboNumb));
            userDetailForGiftVO.getLiveGift().setGiftComboId(giftGivingComboEndReq.getGiftComboId());
            SendMsgVO roomMsgVO = new SendMsgVO();
            roomMsgVO.setContent(JSONObject.toJSONString(userDetailForGiftVO));
            roomMsgVO.setOperatorType(11);
            roomMsgVO.setTargetId(giftGivingComboEndReq.getStudioNum());
            return wbeSocketFeignClient.sendRoomMsg(roomMsgVO);
        } catch (Exception e) {
            log.error("礼物结束连击接口报错：" + e.getMessage());
            return false;
        }
    }

    /**
     * 进入直播简的时候 去查询用户购买的门票是否已经过期了  根据他的购买时间 判断是否超过我们设定的 门票时长
     * 如果没有购买记录 或者过期了 告诉前端没有资格了需要进行续费 或者是购买门票，
     * 如果是 有资格 ，除了需要告诉前端有资格之外 还得返回一个购买的时间
     *
     * @param liveStudioNumReq
     * @return
     */
    public ProductForRoomDto productCheck(LiveStudioNumReq liveStudioNumReq) {
        // 直播间记录的最新studio_log_id
        LiveStudioLog liveStudioLog = liveStudioLogService.selectLastByStudioNum(liveStudioNumReq.getStudioNum());
        ProductForRoomDto productForRoomDto = new ProductForRoomDto();
        if (liveStudioLog.getProductId() == null) {
            productForRoomDto.setIsUsable(-1);
            return productForRoomDto;
        }
        // 礼物赠送记录中类型等于当前直播间的类型
        LiveGiftLogDto liveGiftLog = liveGiftLogService.getFishProductLog(LoginInfoUtil.getUserId(),
                liveStudioLog.getProductId(), liveStudioLog.getLogId());
        // 没有,或者已过期返回false,有返回ture与购买时间
        if (liveGiftLog == null) {
            productForRoomDto.setIsUsable(0);
            return productForRoomDto;
        }

        Date givingLocalTime = liveGiftLog.getGivingLocalTime();
        // 按分钟收费
        if (liveGiftLog.getGiftType() == 6) {
            // 判断是否超过一分钟,超过返回false
            Date endTime = DateUtils.addDate(givingLocalTime, 1, 12);
            // 如果当前时间大于购买时间 + 1分钟,即:过期
            if (SystemUtil.getLangTime().compareTo(endTime) == 1) {
                productForRoomDto.setIsUsable(0);
                return productForRoomDto;
            }
            // 未超过返回结束时间,即:购买时间加1分钟
            productForRoomDto.setIsUsable(1);
            productForRoomDto.setBuyTime(givingLocalTime.getTime());
            productForRoomDto.setEndTime(endTime.getTime());
            // 按场收费
        } else if (liveGiftLog.getGiftType() == 7) {
            productForRoomDto.setBuyTime(givingLocalTime.getTime());
            productForRoomDto.setIsUsable(1);
        }
        return productForRoomDto;
    }


    /**
     * 点击收费房间前请求的接口，点击确认，扣费成功返回true进入房间
     *
     * @param liveStudioNumReq
     * @return
     */
    @Transactional
    public BuyProductVO buyProduct(LiveStudioNumReq liveStudioNumReq) {
        String studioNum = liveStudioNumReq.getStudioNum();
        //获取直播间信息
        LiveStudioList byRoomNum = liveStudioListService.getByRoomNum(studioNum);
        if (byRoomNum == null) {
            throw new BusinessException("直播间不存在！");
        }
        if (byRoomNum.getProductId() == null) {
            throw new BusinessException("当前直播间未开启收费");
        }
        // 游客不可以送礼
        if (LoginInfoUtil.getUserType() == -1) {
            throw new BusinessException(StatusCode.LIVE_GIFT_NOT_TOURIST);
        }
        // 当前直播间状态，如果已关闭，不可送礼
        // 当前直播间信息
        LiveStudioLog liveStudioLog = liveStudioLogService.selectLastByStudioNum(studioNum);
        if (liveStudioLog == null) {
            throw new BusinessException("当前主播开播信息不存在！");
        }
        // 直播间开播缓存
        Object isExist = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.anchor_studioNum, studioNum);
        // 正在关播中
        RLock liveLock = redissonClient.getLock(RedisKeys.SYNC_LIVE_STUDIO_CLOSE + studioNum);
        if (liveLock.isLocked() || isExist == null) {
            throw new BusinessException("当前直播间已经关闭");
        }
        String account = LoginInfoUtil.getUserAccount();
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.SYNC_LIVE_BUY_PRODUCT + account);
        try {
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            if (bool) {
                // 获取当前直播间的每分钟收费银豆额
                LiveGift liveGift = liveGiftService.selectById(byRoomNum.getProductId());
                BigDecimal chargeMoney = liveGift.getPrice();

                Long userId = LoginInfoUtil.getUserId();
                // 获取当前用户的钱包余额，判断大小
                // 3.查询赠送人余额
                QueryWrapper<MemWallet> queryWrapperWallet = new QueryWrapper<>();
                queryWrapperWallet.lambda().eq(MemWallet::getUserId, userId);
                queryWrapperWallet.lambda().eq(MemWallet::getWalletType, 1);
                // 银豆余额
                BigDecimal balance = memWalletService.getOne(queryWrapperWallet).getSilverBean();
                // 余额小于商品价格
                if (balance.compareTo(chargeMoney) == -1) {
                    throw new BusinessException("余额不足");
                }
                // 礼物订单id
                String generateShortId = SnowflakeIdWorker.generateShortId();
                // 5. 用户扣除钱包银豆余额
                MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
                memAccountChangeVO.setOrderNo(generateShortId);
                memAccountChangeVO.setAccount(account);
                memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_1.getCode());
                memAccountChangeVO.setSilverBeanPrice(chargeMoney.multiply(new BigDecimal("-1")));
                memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE29.getPayTypeCode());
                memAccountChangeVO.setOpNote(byRoomNum.getProductId().toString());
                BalanceChangeDTO flagVo = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
                if (!flagVo.getFlag()) {
                    log.error("用户账号：{} 购买门票主播账号：{} 银豆账变失败！", userId, byRoomNum.getUserId());
                    throw new BusinessException("用户账号：" + userId + " 购买门票主播账号：" + byRoomNum.getUserId() + " 银豆账变失败！");
                }

                // 新加购买记录到礼物记录表
                LiveGiftLog liveGiftLog = new LiveGiftLog();
                liveGiftLog.setHostId(byRoomNum.getUserId());
                liveGiftLog.setGivingId(userId);
                liveGiftLog.setGiftId(liveGift.getGiftId());
                liveGiftLog.setGiftNumber(PayConstants.GoldTypeEnum.GOLD_TYPE_1.getCode());
                liveGiftLog.setGiftPrice(liveGift.getPrice());
                liveGiftLog.setStudioLogId(liveStudioLog.getLogId());
                liveGiftLog.setGivingLocalTime(SystemUtil.getLangTime());
                liveGiftLog.setGoldChangeNo(flagVo.getChangeOrderNo());
                liveGiftLog.setGiftLogNo(generateShortId);
                liveGiftLogService.save(liveGiftLog);

                // 记录本场礼物金额到缓存
                Object giftTotalObject = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.live_StudioLog_gift,
                        liveStudioLog.getStudioNum());
                BigDecimal giftTotal = giftTotalObject == null ? chargeMoney
                        : new BigDecimal(giftTotalObject.toString()).add(chargeMoney);
                ApiBusinessRedisUtils.hset(WebSocketRedisKeys.live_StudioLog_gift, liveStudioLog.getStudioNum(),
                        giftTotal);

                // 通知主播更新本场直播收礼金额
                SendMsgVO megToAnchor = new SendMsgVO();
                megToAnchor.setContent(giftTotal.toString());
                megToAnchor.setOperatorType(12);
                megToAnchor.setTargetId(byRoomNum.getUserId().toString());
                wbeSocketFeignClient.sendSingleMsg(megToAnchor);
                BuyProductVO vo = new BuyProductVO();
                if (liveGift.getGiftType() == 6) {//按时收费处理
                    //TODO 商品购买时长 目前是写死在代码里，后期在收费商品属性里面获取时长。
                    // 设置当前用户 购买的观看时间 单位秒（）
                    // 直播结束 需要清除缓存
                    ApiBusinessRedisUtils.set(WebSocketRedisKeys.studioNum_Try_See_User_Id + liveStudioNumReq.getStudioNum() + ":" + userId, byRoomNum.getProductId(), 60L);
                    vo.setOutTime(60L);
                } else if (liveGift.getGiftType() == 7) {
                    //按场收费处理 没有过期时间 直播结束 需要清除缓存
                    ApiBusinessRedisUtils.set(WebSocketRedisKeys.studioNum_Try_See_User_Id + liveStudioNumReq.getStudioNum() + ":" + userId, byRoomNum.getProductId());
                }
                vo.setFlag(flagVo.getFlag());
                return vo;

            } else {
                log.error("buyProduct 用户账号:{}购买门票没拿到锁, 购买门票失败 ", account);
                throw new BusinessException("正在处理中");
            }
        } catch (Exception e) {
            log.error("buyProduct 用户账号:" + account + "购买门票异常, 购买门票失败 ", e);
            throw new BusinessException("购买门票异常，请联系管理员");
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("buyProduct 用户账号:{} 购买门票结束，释放锁 ", account);
        }
    }


    /**
     * 用户试看超时记录
     *
     * @param liveStudioNumReq
     * @return
     */
    public void trySeeExpire(LiveStudioNumReq liveStudioNumReq) {
        ApiBusinessRedisUtils.sSet(WebSocketRedisKeys.studioNum_Try_See_User_Id + liveStudioNumReq.getStudioNum(),
                LoginInfoUtil.getUserId().toString());
    }


}
