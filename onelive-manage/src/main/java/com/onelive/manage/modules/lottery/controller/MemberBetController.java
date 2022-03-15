package com.onelive.manage.modules.lottery.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.lottery.MemberBetReq;
import com.onelive.common.model.req.lottery.OrderCancleByIssueReq;
import com.onelive.common.model.req.lottery.OrderSettleByIdReq;
import com.onelive.common.model.req.lottery.OrderSettleByIssueReq;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.lottery.BetLotterySelectVO;
import com.onelive.common.model.vo.lottery.MemberBetVO;
import com.onelive.common.model.vo.lottery.OrderBetRecordDetailVO;
import com.onelive.manage.modules.lottery.business.MemberBetBussiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/bet")
@Api(tags = "运营管理-注单详情")
public class MemberBetController {

    private static final Logger logger = LoggerFactory.getLogger(MemberBetController.class);
    @Resource
    private MemberBetBussiness memberBetBussiness;

    @ApiOperation("中奖状态下拉列表")
    @GetMapping(name = "中奖状态下拉列表", value = "/getBetStatusSelect")
    public ResultInfo<List<SelectStringVO>> getBetStatusSelect(){
         return ResultInfo.ok(memberBetBussiness.getBetStatusSelect()) ;
    }

    @ApiOperation("彩种下拉-玩法下拉返回类")
    @GetMapping(name = "彩种下拉-玩法下拉返回类", value = "/getBetLotterySelect")
    public ResultInfo<List<BetLotterySelectVO>> getBetLotterySelect(){
        return ResultInfo.ok(memberBetBussiness.getBetLotterySelect()) ;
    }

    @ApiOperation("注单详情列表")
    @PostMapping(name = "注单的投注管理列表", value = "/listOrderBet")
    public ResultInfo<PageInfo<MemberBetVO>> listOrderBet(@RequestBody(required = false) MemberBetReq req) {
        if(req == null){
            req = new MemberBetReq();
            req.setPageNum(1);
            req.setPageSize(10);
        }
        return ResultInfo.ok(memberBetBussiness.getListOrderBet(req));
    }

    /**
     * 根据id查询注单
     *
     * @return
     */
    @ApiOperation("根据id查询注单信息")
    @GetMapping(name = "根据id查询注单信息", value = "/getOrderBetById")
    public ResultInfo<OrderBetRecordDetailVO> getOrderBetById(@RequestParam("id") Integer id) {
        return ResultInfo.ok(memberBetBussiness.getOrderBetById(id));
    }

    @ApiOperation("结算")
    @PostMapping(name = "结算注单", value = "/settleOrderBetById")
    public ResultInfo<Boolean> settleOrderBetById(@RequestBody(required = false) OrderSettleByIdReq req) {
        return memberBetBussiness.settleOrderBetById(req);
    }

    @ApiOperation("期号结算")
    @PostMapping(name = "根据期号结算订单", value = "/settleOrderBetByIssue")
    public ResultInfo<Boolean> settleOrderBetByIssue(@RequestBody(required = false) OrderSettleByIssueReq req) {
        return memberBetBussiness.settleOrderBetByIssue(req);
    }

    @ApiOperation("期号撤单")
    @PostMapping(name = "根据期号结算订单", value = "/cancelOrderBetByIssue")
    public ResultInfo<Boolean> cancelOrderBetByIssue(@RequestBody(required = false) OrderCancleByIssueReq req) {
        return memberBetBussiness.cancelOrderBetByIssue(req);
    }

}
