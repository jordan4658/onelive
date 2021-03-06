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
     * ??????????????????????????????TODO ?????????????????????????????????????????????????????????????????????
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
     * ????????????id?????????????????????
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

            // 1. ??????????????????
            LiveGift barrage = liveGiftService.getBarrage();
            BigDecimal barragePrice = barrage == null ? new BigDecimal(0) : barrage.getPrice();
            vo.setBarragePrice(barragePrice);
            // ?????????????????????
            vo.setAnchorId(memUserAnchor.getUserId());

            vo.setStudioId(liveStudio.getStudioId());
            vo.setStudioNum(studioNum);
            vo.setUserId(liveStudio.getUserId());
            vo.setTrySeeTime(liveStudio.getTrySeeTime());
            vo.setProductId(liveStudio.getProductId());
            vo.setStudioStatus(liveStudio.getStudioStatus());
            // ?????????
            Integer studioNum_Heat = (Integer) ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.studioNum_Heat, studioNum);
            vo.setFirepower(studioNum_Heat);
            // TODO ????????????
            vo.setGuardNum(0);
            vo.setOnlineNum(onlineUsersCount(studioNum));

            // ??????????????????
            LiveAnchorVO anchorVO = new LiveAnchorVO();
            anchorVO.setUserId(liveStudio.getUserId());
            anchorVO.setNickName(memUser.getNickName());
            anchorVO.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
            anchorVO.setIsFocus(memFocusUserService.isExistFocus(liveStudio.getUserId()));
            // ???????????????????????????
            int fansCount = memFocusUserService.fansCount(liveStudio.getUserId());
            anchorVO.setFansCount(fansCount);
            vo.setLiveAnchorVO(anchorVO);

            Boolean isSelectGame = liveStudio.getGameId() != null;
            // ?????????,??????/??????id
            vo.setIsSelectGame(isSelectGame);
            if (isSelectGame) {
                Lottery lottery = lotteryService.getByLotteryId(liveStudio.getGameId(), LoginInfoUtil.getLang());
                if (lottery != null) {
                    vo.setGameId(liveStudio.getGameId());
                    vo.setGameIcon(AWSS3Util.getAbsoluteUrl(lottery.getIcon()));
                    vo.setGameName(lottery.getName());
                    vo.setLotteryId(lottery.getLotteryId());
                    // ????????????(???)
                    vo.setEndTime(lottery.getEndTime());
                }
            }

            // ???????????????????????????,????????? ?????????????????????
            List<LiveUserDetailVO> onlineList = roomService.onlineUsers(studioNum);
            vo.setOnlineList(onlineList);

            // ????????????????????? TODO ??????????????????
            LiveRoomConfig liveRoomConfig = new LiveRoomConfig();
            liveRoomConfig.setSpeakLevel(2);
            liveRoomConfig.setBarrageLevel(10);
            liveRoomConfig.setFocusPopuTime(120);
            liveRoomConfig.setFocusStayTime(5);
            liveRoomConfig.setIsQuitRoomShowFocus(true);
            liveRoomConfig.setIsOpenBarrage(barrage == null ? false : true);

            // ??????????????? 1???????????????2????????? 3?????????????????????
            Integer enterType = 1;
            Object kicking = ApiBusinessRedisUtils.hGet(WebSocketRedisKeys.kicking_room_key + studioNum,
                    LoginInfoUtil.getUserId().toString());
            if (kicking != null) {
                enterType = 2;
            }
            long time = ApiBusinessRedisUtils.getExpire(WebSocketRedisKeys.studioNum_Try_See_User_Id + studioNum + ":" + LoginInfoUtil.getUserId().toString());
            if (time == -2L) {//???????????????????????????
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
     * ?????????????????????????????????
     *
     * @return
     */
    public Integer onlineUsersCount(String studioNum) {
        return liveStudioListService.onlineUsersCount(studioNum);
    }

    /**
     * ?????????????????????
     * <p>
     * TODO ???????????????
     *
     * @param req
     * @return
     * @throws Exception
     */
    public List<LiveGuardListVO> getLiveGuardList(LiveGuardReq req) throws Exception {
        List<LiveGuardListVO> list = new ArrayList<>();
        // ??????????????? TODO ???????????????????????????????????????????????????*100 ?????????????????????
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
     * ??????Tab???????????????
     *
     * @return
     * @throws Exception
     */
    public List<LiveTabItemVo> getLiveTabItemList() throws Exception {

        return liveTabItemService.getLiveTabItemList();
    }

    /**
     * ????????????????????????????????????
     *
     * @param req
     * @return
     */
    public List<LiveStudioListForIndexVO> getLiveRecommendList(LiveRecommendReq req) throws Exception {
        List<LiveStudioListForIndexVO> list = liveStudioListService.getLiveRecommendListVO(req.getStudioNum());
        return list;
    }

    /**
     * ???????????????????????????
     *
     * @param userId ???????????????ID
     * @return
     */
    public LiveAnchorInfoVO getAnchorInfo(Long userId) {
        LiveAnchorInfoVO vo = memUserService.getAnchorInfoById(userId);
        vo.setAvatar(AWSS3Util.getAbsoluteUrl(vo.getAvatar()));
        return vo;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     * ?????????????????????????????????30???????????????????????????????????????????????????(30???????????????????????????????????????????????????????????????)???
     * ????????????/??????????????????????????????
     *
     * @param req
     * @return
     */
    public Boolean switchCharge(LiveSwitchChargeReq req) {
        LiveStudioList liveStudioList = liveStudioListService.getByUserId(LoginInfoUtil.getUserId());
        long switch_charge = ApiBusinessRedisUtils.getExpire(RedisKeys.STUDIO_SWITCH_CHARGE + liveStudioList.getStudioNum());
        if (switch_charge > 0) {
            throw new BusinessException(DateUtils.changeTimeFormat(switch_charge) + "???????????????????????????");
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
     * ???????????????????????????
     *
     * @return
     */
    public List<ProductChargeDto> roomProducts(ProductTypeDto productTypeDto) {
        List<ProductChargeDto> productChargeDtos = liveGiftService.roomProducts(productTypeDto);
        return productChargeDtos;
    }

}