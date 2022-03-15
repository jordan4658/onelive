package com.onelive.manage.modules.lottery.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.lottery.*;
import com.onelive.common.model.vo.lottery.*;
import com.onelive.manage.modules.lottery.business.LotteryBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/lottery")
@Api(tags = "游戏管理-彩种管理")
@Slf4j
public class LotteryController {

    @Resource
    private LotteryBusiness lotteryBusiness;

    @PostMapping("/queryLotteryList")
    @ApiOperation("查询彩种列表")
    public ResultInfo<PageInfo<LotteryVO>> queryLotteryList(@RequestBody(required = false) LotteryQueryReq req) {
        return ResultInfo.ok(lotteryBusiness.queryLotteryList(req));
    }

    @PostMapping("/querySelectLotteryList")
    @ApiOperation("查询彩种列表-用于选择")
    public ResultInfo<List<LotterySelectVO>> querySelectLotteryList() {
        return ResultInfo.ok(lotteryBusiness.querySelectLotteryList());
    }

/*

    @PostMapping("/addLottery")
    @ApiOperation("新增彩种")
    public ResultInfo<Boolean> addLottery(@RequestBody LotteryAddReq req) {
        lotteryBusiness.addLottery(req);
        return ResultInfo.ok();
    }
*/

    @PostMapping("/getLottery")
    @ApiOperation("根据主键获取彩种信息")
    public ResultInfo<LotteryEditVO> getLottery(@RequestBody LotteryIdReq req) {
        return ResultInfo.ok(lotteryBusiness.getLotteryById(req));
    }

    @PostMapping("/saveLottery")
    @ApiOperation("保存彩种")
    public ResultInfo<Boolean> saveLottery(@RequestBody LotterySaveReq req) {
        lotteryBusiness.saveLottery(req);
        return ResultInfo.ok();
    }

    @PostMapping("/delLottery")
    @ApiOperation("删除彩种")
    public ResultInfo<Boolean> delLottery(@RequestBody LotteryIdReq req) {
        lotteryBusiness.delLottery(req);
        return ResultInfo.ok();
    }

    @PostMapping("/queryPlayList")
    @ApiOperation("获取方案列表数据")
    public ResultInfo<List<LotteryPlayListVO>> queryPlayList(@RequestBody LotteryPlayReq req) {
        List<LotteryPlayListVO> list =  lotteryBusiness.queryPlayList(req);
        return ResultInfo.ok(list);
    }

    @PostMapping("/queryPlayOddsList")
    @ApiOperation("获取方案赔率列表数据")
    public ResultInfo<List<LotteryPlayOddsListVO>> queryPlayOddsList(@RequestBody LotteryPlayOddsReq req) {
        List<LotteryPlayOddsListVO> list =  lotteryBusiness.queryPlayOddsList(req);
        return ResultInfo.ok(list);
    }

    @PostMapping("/getPlayInfo")
    @ApiOperation("获取方案详细数据")
    public ResultInfo<LotteryPlayVO> getPlayInfo(@RequestBody LongIdReq req) {
        return ResultInfo.ok(lotteryBusiness.getPlayInfo(req));
    }


    @PostMapping("/savePlay")
    @ApiOperation("保存玩法（方案）信息")
    public ResultInfo<Boolean> savePlay(@RequestBody LotteryPlaySaveReq req) {
        lotteryBusiness.savePlay(req);
        return ResultInfo.ok();
    }

    @PostMapping("/delLotteryPlay")
    @ApiOperation("删除对应的玩法（方案）")
    public ResultInfo<Boolean> delLotteryPlay(@RequestBody LotteryIdReq req) {
        lotteryBusiness.delLotteryPlay(req);
        return ResultInfo.ok();
    }

    @PostMapping("/getPlaySettingInfo")
    @ApiOperation("获取玩法规则信息")
    public ResultInfo<LotteryPlaySettingVO> getPlaySettingInfo(@RequestBody LotteryPlaySettingQueryReq req){
       return  ResultInfo.ok(lotteryBusiness.getPlaySettingInfo(req));
    }

    @PostMapping("/savePlaySetting")
    @ApiOperation("保存玩法规则设置")
    public ResultInfo<Boolean> savePlaySetting(@RequestBody LotteryPlaySettingSaveReq req) {
        lotteryBusiness.savePlaySetting(req);
        return ResultInfo.ok();
    }


    @PostMapping("/savePlayOdds")
    @ApiOperation("保存玩法赔率信息")
    public ResultInfo<Boolean> savePlayOdds(@RequestBody LotteryPlayOddsSaveReq req) {
        lotteryBusiness.savePlayOdds(req);
        return ResultInfo.ok();
    }

    @PostMapping("/delPlayOdds")
    @ApiOperation("删除对应的赔率信息")
    public ResultInfo<Boolean> delPlayOdds(@RequestBody LotteryIdReq req) {
        lotteryBusiness.delPlayOdds(req);
        return ResultInfo.ok();
    }

    @PostMapping("/getPlayOdds")
    @ApiOperation("查询玩法赔率信息")
    public ResultInfo<LotteryPlayOddsVO> getPlayOdds(@RequestBody LotteryIdReq req) {
        return ResultInfo.ok(lotteryBusiness.getPlayOdds(req));
    }

    @GetMapping("/getSelectLotteryList")
    @ApiOperation("彩种类型下拉-彩种名称下拉")
    public ResultInfo<List<CategorySelectVO>> getSelectLotteryList() {
        return ResultInfo.ok(lotteryBusiness.getSelectLotteryList());
    }

}
