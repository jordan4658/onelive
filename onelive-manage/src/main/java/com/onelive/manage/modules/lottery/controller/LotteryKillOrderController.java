package com.onelive.manage.modules.lottery.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.lottery.LotteryKillOrdersReq;
import com.onelive.common.model.vo.lottery.LotteryKillOrdersVO;
import com.onelive.common.model.vo.lottery.LotteryPlatfomVO;
import com.onelive.manage.modules.lottery.business.LotteryKillOrderBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/lotteryKillOrder")
@Api(tags = "游戏管理-杀号配置")
@Slf4j
public class LotteryKillOrderController {

    @Resource
    private LotteryKillOrderBusiness lotteryKillOrderBusiness;


    @GetMapping("/queryPlatform")
    @ApiOperation("获取平台标识列表")
    public ResultInfo<List<LotteryPlatfomVO>> queryPlatform(){
        return  ResultInfo.ok(lotteryKillOrderBusiness.queryPlatform());
    }


    @GetMapping("/queryKillList")
    @ApiOperation("获取杀号配置列表数据")
    public ResultInfo<List<LotteryKillOrdersVO>> queryKillList(){
        return  ResultInfo.ok(lotteryKillOrderBusiness.queryKillList());
    }

    @PostMapping("/updateKillConfig")
    @ApiOperation("更新杀号配置信息")
    public ResultInfo<Boolean> updateKillConfig(@RequestBody LotteryKillOrdersReq req){
        lotteryKillOrderBusiness.updateKillConfig(req);
        return  ResultInfo.ok();
    }

}
