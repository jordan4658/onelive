package com.onelive.api.modules.lottery.controller;


import com.onelive.api.modules.lottery.business.LotteryBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.client.OneliveOrderClient;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.lottery.LotteryInfoDTO;
import com.onelive.common.model.req.live.AppLiveGameListReq;
import com.onelive.common.model.req.lottery.LotteryGameCodeReq;
import com.onelive.common.model.req.lottery.LotteryGameNoReq;
import com.onelive.common.model.vo.live.game.AppLiveGameListVO;
import com.onelive.common.model.vo.lottery.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description:  彩票接口
 */
@RestController
@RequestMapping("/lottery")
@Api(tags = "彩票接口")
@Slf4j
public class LotteryController extends BaseController {

    @Resource
    private LotteryBusiness lotteryBusiness;

    @Resource
    private OneliveOrderClient oneliveOrderClient;

    @ApiOperation("主播开房间时候，可以选择的彩票列表")
    @PostMapping(value = {"/app/v1/getLotteryRoomList", "/pc/v1/getLotteryRoomList"})
    @AllowAccess
    public ResultInfo<List<LotteryRoomVO>> getLotteryRoomList() {
       return ResultInfo.ok(lotteryBusiness.getLotteryRoomList());
    }

    @ApiOperation("获取彩种赔率玩法")
    @PostMapping(value = {"/app/v1/queryLotteryVersionZIP","/pc/v1/queryLotteryVersionZIP"})
    @AllowAccess
    public ResultInfo<LotteryZIPVO> queryLotteryVersionZIP() {
        return ResultInfo.ok(lotteryBusiness.queryLotteryVersionZIP());
    }

    @ApiOperation("直播间-游戏code值列表(标签列表)")
    @PostMapping(value = {"/app/v1/getGameCodeList","/pc/v1/getGameCodeList"})
    @AllowAccess
    public ResultInfo<List<LotteryGameCodeListVO>> getGameCodeList() {
        return ResultInfo.ok(lotteryBusiness.getGameCodeList());
    }

    @ApiOperation("直播间-直播间游戏列表")
    @PostMapping(value = {"/app/v1/getLiveGameList","/pc/v1/getLiveGameList"})
    @AllowAccess
    public ResultInfo<List<LotteryGameListVO>> getLiveGameList(@RequestBody LotteryGameCodeReq req) {
        return ResultInfo.ok(lotteryBusiness.getLiveGameList(req.getCode()));
    }

    @ApiOperation("直播间-判断第三方游戏是否可以跳转")
    @PostMapping(value = {"/app/v1/checkThirdCanJump","/pc/v1/checkThirdCanJump"})
    @AllowAccess
    @ApiIgnore
    public ResultInfo<LotteryGameCheckJumpVO> checkThirdCanJump(@RequestBody LotteryGameNoReq req) {
        return ResultInfo.ok(lotteryBusiness.checkThirdCanJump(req.getGameNo()));
    }


    /**
     * 获取彩种列表-非第三方
     *
     * @return
     */
    @ApiOperation("获取彩种列表-非第三方")
    @RequestMapping(name = "获取彩种列表-非第三方", value = "/queryLotteryList", method = RequestMethod.POST)
    public ResultInfo<List<Map<String, Object>>> queryLotteryList() {
        return oneliveOrderClient.queryLotteryList();
    }



    @ApiOperation("获取所有彩种配置，非第三方")
    @RequestMapping(name = "获取所有彩种配置，非第三方", value = "/queryAllLotteryList", method = RequestMethod.POST)
    public ResultInfo<List<LotteryInfoDTO>> queryAllLotteryList() {
        ResultInfo<List<LotteryInfoDTO>> resultInfo = oneliveOrderClient.queryAllLotteryList();
        return resultInfo;
    }



    @ApiOperation("直播间-游戏标签列表")
    @PostMapping(value = {"/app/v1/getGameTagList","/pc/v1/getGameTagList"})
    @AllowAccess
    @ApiIgnore
    public ResultInfo<List<LiveGameTagListVO>> getGameTagList() {
        return ResultInfo.ok(lotteryBusiness.getGameTagList());
    }

    @ApiOperation("直播间-游戏列表")
    @PostMapping(value = {"/app/v1/getGameListByTag","/pc/v1/getGameListByTag"})
    @AllowAccess
    @ApiIgnore
    public ResultInfo<List<AppLiveGameListVO>> getGameListByTag(@RequestBody AppLiveGameListReq req) {
        return ResultInfo.ok(lotteryBusiness.getGameListByTag(req));
    }
}
