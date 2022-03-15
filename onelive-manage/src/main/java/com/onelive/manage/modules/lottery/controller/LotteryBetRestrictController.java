package com.onelive.manage.modules.lottery.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.lottery.LotteryRestrictEditReq;
import com.onelive.common.model.vo.lottery.RestrictLotteryCategoryVO;
import com.onelive.common.model.vo.lottery.RestrictLotteryPlayVO;
import com.onelive.manage.modules.lottery.business.LotteryBetRestrictBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/lottery/bonus")
@Api(tags = "游戏管理-投注限制設置")
@Slf4j
public class LotteryBetRestrictController {

    @Resource
   private LotteryBetRestrictBusiness lotteryBetRestrictBusiness;

    @GetMapping("/getRestrictLotteryList")
    @ApiOperation("投注限制設置-彩种类型下拉-彩种名称下拉")
    public ResultInfo<List<RestrictLotteryCategoryVO>> getRestrictLotteryList() {
        return ResultInfo.ok(lotteryBetRestrictBusiness.getRestrictLotteryList());
    }

    @GetMapping("/getPlayList")
    @ApiOperation("通过彩种id获取玩法列表")
    public ResultInfo<List<RestrictLotteryPlayVO>> getPlayList(@ApiParam("彩种id") @RequestParam(name = "lotteryId") Integer lotteryId) {
        return ResultInfo.ok(lotteryBetRestrictBusiness.getPlayList(lotteryId));
    }

    @PostMapping("/savaBetRestrict")
    @ApiOperation("保存投注限制")
    public ResultInfo<BigDecimal> savaBetRestrict(@RequestBody LotteryRestrictEditReq req) {
        return ResultInfo.ok(lotteryBetRestrictBusiness.savaBetRestrict(req));
    }



}
