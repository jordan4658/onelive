//package com.onelive.manage.modules.lottery.controller;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.onelive.common.model.common.ResultInfo;
//import com.onelive.common.model.req.lottery.LotteryCountryReq;
//import com.onelive.common.model.req.lottery.LotteryCountryUpdateReq;
//import com.onelive.common.model.req.lottery.LotteryCountryUpdateStatusReq;
//import com.onelive.common.model.vo.lottery.LotteryCountryVO;
//import com.onelive.manage.modules.lottery.business.LotteryCountryBusiness;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
// 没有地方用到这个内容, 后面准备删除
//@RestController
//@RequestMapping(value = "/lotteryCountry")
//@Api(tags = "直播管理-直播游戏")
//@Slf4j
//public class LotteryCountryController {
//
//    @Resource
//    private LotteryCountryBusiness LotteryCountryBusiness;
//
//    @GetMapping("/queryLotteryCountry")
//    @ApiOperation("根据地区返回直播游戏列表")
//    public ResultInfo<List<LotteryCountryVO>> queryLotteryCountry(LotteryCountryReq req) {
//        return ResultInfo.ok(LotteryCountryBusiness.queryLotteryCountry(req));
//    }
//
//    @PostMapping("/updateInfo")
//    @ApiOperation("更新直播游戏信息")
//    public ResultInfo<Boolean> updateInfo(@RequestBody LotteryCountryUpdateReq req) {
//        LotteryCountryBusiness.updateInfo(req);
//        return ResultInfo.ok();
//    }
//
//    @PostMapping("/changeStatus")
//    @ApiOperation("更新游戏状态")
//    public ResultInfo<Boolean> changeStatus(@RequestBody LotteryCountryUpdateStatusReq req) {
//        LotteryCountryBusiness.changeStatus(req);
//        return ResultInfo.ok();
//    }
//
//}
