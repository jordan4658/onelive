package com.onelive.api.modules.live.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.live.RoomService;
import com.onelive.api.service.lottery.LotteryCountryService;
import com.onelive.api.service.lottery.LotteryService;
import com.onelive.api.service.mem.MemFocusUserService;
import com.onelive.api.service.mem.MemUserAnchorService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.platform.LiveGiftService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.service.tab.LiveTabItemService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.base.BaseController;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.enums.LiveActTypeEnums;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.platform.ProductChargeDto;
import com.onelive.common.model.dto.platform.ProductTypeDto;
import com.onelive.common.model.req.live.LiveGuardReq;
import com.onelive.common.model.req.live.LiveRecommendReq;
import com.onelive.common.model.req.live.LiveSwitchChargeReq;
import com.onelive.common.model.vo.live.LiveActListVO;
import com.onelive.common.model.vo.live.LiveActVO;
import com.onelive.common.model.vo.live.LiveAnchorInfoVO;
import com.onelive.common.model.vo.live.LiveAnchorVO;
import com.onelive.common.model.vo.live.LiveGuardListVO;
import com.onelive.common.model.vo.live.LiveRoomConfig;
import com.onelive.common.model.vo.live.LiveRoomDetailVo;
import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;
import com.onelive.common.model.vo.live.LiveTabItemVo;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.mybatis.entity.LiveGift;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.img.HeaderImgRamUtil;
import com.onelive.common.utils.others.DateUtils;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.common.utils.upload.AWSS3Util;

@Component
public class LiveBusiness extends BaseController {

    @Autowired
    private LiveStudioListService liveStudioListService;
    @Autowired
    private MemUserAnchorService memUserAnchorService;
    @Autowired
    private MemUserService memUserService;
    @Autowired
    private MemFocusUserService memFocusUserService;
    @Autowired
    private LotteryCountryService lotteryCountryService;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private LiveTabItemService liveTabItemService;
    @Resource
    private RoomService roomService;

    @Resource
    private LiveGiftService liveGiftService;

    @Resource
    private SysParameterService sysParameterService;

    /**
     * 返回直播间活动信息，TODO 后面需要读取后台配置的活动管理，目前先做假数据
     *
     * @param type
     * @return
     */
    public LiveActVO getRoomAct(Integer type) {
        LiveActVO vo = new LiveActVO();
        if (LiveActTypeEnums.GAME.getCode() == type) {

        } else if (LiveActTypeEnums.LIVE.getCode() == type) {

        } else {

        }
        List<LiveActListVO> list = new ArrayList<>();
        LiveActListVO vo1 = new LiveActListVO();
        vo1.setId(1l);
        vo1.setImgUrl("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-13.jpg");
        vo1.setLink("https://www.google.com/");

        LiveActListVO vo2 = new LiveActListVO();
        vo2.setId(2l);
        vo2.setImgUrl("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-18.jpg");
        vo2.setLink("https://www.google.com/");

        LiveActListVO vo3 = new LiveActListVO();
        vo3.setId(3l);
        vo3.setImgUrl("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-20.jpg");
        vo3.setLink("https://www.google.com/");

        list.add(vo1);
        list.add(vo2);
        list.add(vo3);

        List<LiveActListVO> liveList = new ArrayList<>();
        LiveActListVO vo4 = new LiveActListVO();
        vo4.setId(1l);
        vo4.setImgUrl("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-13.jpg");
        vo4.setLink("https://www.google.com/");

        LiveActListVO vo5 = new LiveActListVO();
        vo5.setId(2l);
        vo5.setImgUrl("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-18.jpg");
        vo5.setLink("https://www.google.com/");

        LiveActListVO vo6 = new LiveActListVO();
        vo6.setId(3l);
        vo6.setImgUrl("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-20.jpg");
        vo6.setLink("https://www.google.com/");

        liveList.add(vo4);
        liveList.add(vo5);
        liveList.add(vo6);

        vo.setGameList(list);
        vo.setLiveList(liveList);
        return vo;

    }

    /**
     * 通过房间id获取直播间详情
     *
     * @return
     */
    public LiveRoomDetailVo getRoomDetail(String studioNum) throws Exception {
        if (StringUtils.isBlank(studioNum)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LiveRoomDetailVo vo = new LiveRoomDetailVo();
        LiveStudioList liveStudio = liveStudioListService.getByRoomNum(studioNum);
        if (liveStudio != null) {
            MemUserAnchor memUserAnchor = memUserAnchorService.getInfoByUserId(liveStudio.getUserId());
            MemUser memUser = memUserService.getById(liveStudio.getUserId());

            // 1. 查询弹幕价格
            LiveGift barrage = liveGiftService.getBarrage();
            BigDecimal barragePrice = barrage == null ? new BigDecimal(0) : barrage.getPrice();
            vo.setBarragePrice(barragePrice);
            // 直播间基本信息
            vo.setAnchorId(memUserAnchor.getUserId());

            vo.setStudioId(liveStudio.getStudioId());
            vo.setStudioNum(studioNum);
            vo.setUserId(liveStudio.getUserId());
            vo.setTrySeeTime(liveStudio.getTrySeeTime());
            vo.setProductId(liveStudio.getProductId());
            vo.setStudioStatus(liveStudio.getStudioStatus());
            // 火力值
            Integer studioNum_Heat = (Integer) ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, studioNum);
            vo.setFirepower(studioNum_Heat);
            // TODO 守护人数
            vo.setGuardNum(0);
            vo.setOnlineNum(onlineUsersCount(studioNum));

            // 主播简单信息
            LiveAnchorVO anchorVO = new LiveAnchorVO();
            anchorVO.setUserId(liveStudio.getUserId());
            anchorVO.setNickName(memUser.getNickName());
            anchorVO.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
            anchorVO.setIsFocus(memFocusUserService.isExistFocus(liveStudio.getUserId()));
            // 当前主播的粉丝数量
            int fansCount = memFocusUserService.fansCount(liveStudio.getUserId());
            anchorVO.setFansCount(fansCount);
            vo.setLiveAnchorVO(anchorVO);

            Boolean isSelectGame = liveStudio.getGameId() != null;
            // 直播间,彩票/游戏id
            vo.setIsSelectGame(isSelectGame);
            if (isSelectGame) {
                Lottery lottery = lotteryService.getByLotteryId(liveStudio.getGameId(), LoginInfoUtil.getLang());
                if (lottery != null) {
                    vo.setGameId(liveStudio.getGameId());
                    vo.setGameIcon(AWSS3Util.getAbsoluteUrl(lottery.getIcon()));
                    vo.setGameName(lottery.getName());
                    vo.setLotteryId(lottery.getLotteryId());
                    // 封盘时间(秒)
                    vo.setEndTime(lottery.getEndTime());
                }
            }

            // 直播间在线人数列表,前五十 ，按照等级排名
            List<LiveUserDetailVO> onlineList = roomService.onlineUsers(studioNum);
            vo.setOnlineList(onlineList);

            // 直播间公共配置 TODO 读取后台数据
            LiveRoomConfig liveRoomConfig = new LiveRoomConfig();
            liveRoomConfig.setSpeakLevel(2);
            liveRoomConfig.setBarrageLevel(10);
            liveRoomConfig.setFocusPopuTime(120);
            liveRoomConfig.setFocusStayTime(5);
            liveRoomConfig.setIsQuitRoomShowFocus(true);
            liveRoomConfig.setIsOpenBarrage(barrage == null ? false : true);

            // 是否可进入 1可以进入，2：被踢 3：收费超时观看
            Integer enterType = 1;
            Object kicking = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.kicking_room_key + studioNum,
                    LoginInfoUtil.getUserId().toString());
            if (kicking != null) {
                enterType = 2;
            }
            long time = ApiBusinessRedisUtils.getExpire(WebSocketRedisKeys.studioNum_Try_See_User_Id + studioNum + ":" + LoginInfoUtil.getUserId().toString());
            if (time == -2L) {//表示没有观看时长了
                enterType = 3;
            }else{
                liveRoomConfig.setRemainingTime(time);
            }
            liveRoomConfig.setEnterType(enterType);
            vo.setRoomConfig(liveRoomConfig);
        }
        return vo;
    }

    /**
     * 查询直播间在线观众数量
     *
     * @return
     */
    public Integer onlineUsersCount(String studioNum) {
        return liveStudioListService.onlineUsersCount(studioNum);
    }

    /**
     * 获取守护榜列表
     * <p>
     * TODO 一期先不做
     *
     * @param req
     * @return
     * @throws Exception
     */
    public List<LiveGuardListVO> getLiveGuardList(LiveGuardReq req) throws Exception {
        List<LiveGuardListVO> list = new ArrayList<>();
        // 暂时先写死 TODO 获取进入直播间后用户送礼物总金币数*100 ，来计算火力值
        int num = 30;
        for (int i = 0; i < num; i++) {
            LiveGuardListVO vo = new LiveGuardListVO();
            vo.setRankNo(i + 1);
            vo.setUserId(1l);
            vo.setAvatar(AWSS3Util.getAbsoluteUrl(HeaderImgRamUtil.userHeadImg()));
            vo.setLevel(0);
            vo.setLevelName("VIP0");
            vo.setLevelIcon(
                    "https://a2x3z.s3.ap-southeast-1.amazonaws.com/online/web/Js%2B1zqERMTRczKkUZ5wsrg==/20210707-1b689af8c9c5426eb6b8351fcf2abed4.png");
            list.add(vo);
        }
        return list;
    }

    /**
     * 获取Tab栏配置信息
     *
     * @return
     * @throws Exception
     */
    public List<LiveTabItemVo> getLiveTabItemList() throws Exception {

        return liveTabItemService.getLiveTabItemList();
    }

    /**
     * 当前直播间的直播房间列表
     *
     * @param req
     * @return
     */
    public List<LiveStudioListForIndexVO> getLiveRecommendList(LiveRecommendReq req) throws Exception {
        List<LiveStudioListForIndexVO> list = liveStudioListService.getLiveRecommendListVO(req.getStudioNum());
        return list;
    }

    /**
     * 查询主播的详细信息
     *
     * @param userId 主播的用户ID
     * @return
     */
    public LiveAnchorInfoVO getAnchorInfo(Long userId) {
        LiveAnchorInfoVO vo = memUserService.getAnchorInfoById(userId);
        vo.setAvatar(AWSS3Util.getAbsoluteUrl(vo.getAvatar()));
        return vo;
    }

    /**
     * 直播中只允许从免费直播间切换到收费直播间一次；
     * 如要更换其它模式，需要30分钟冷却时间后，主播下播再重新开播(30分钟冷却时间内，即使重新开播，收费模式不变)；
     * 主播开启/关闭直播间状态为收费
     *
     * @param req
     * @return
     */
    public Boolean switchCharge(LiveSwitchChargeReq req) {
        LiveStudioList liveStudioList = liveStudioListService.getByUserId(LoginInfoUtil.getUserId());
        long switch_charge = ApiBusinessRedisUtils.getExpire(RedisKeys.STUDIO_SWITCH_CHARGE + liveStudioList.getStudioNum());
        if (switch_charge > 0) {
            throw new BusinessException(DateUtils.changeTimeFormat(switch_charge) + "后可以切换收费模式");
        }

        Integer productId = req.getProductId() == null ? 0 : req.getProductId();

        liveStudioList.setProductId(productId);
        boolean updateById = liveStudioListService.updateById(liveStudioList);
        if (updateById) {
            ApiBusinessRedisUtils.set(RedisKeys.STUDIO_SWITCH_CHARGE + liveStudioList.getStudioNum(), 1, 60 * 30L);
        }
        return updateById;
    }

    /**
     * 查询直播间收费商品
     *
     * @return
     */
    public List<ProductChargeDto> roomProducts(ProductTypeDto productTypeDto) {
        List<ProductChargeDto> productChargeDtos = liveGiftService.roomProducts(productTypeDto);
        return productChargeDtos;
    }

}