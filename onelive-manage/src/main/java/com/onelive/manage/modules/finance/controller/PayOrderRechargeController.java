package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.OfflineRechargeHandleReq;
import com.onelive.common.model.vo.pay.OfflinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.pay.OnlinePayOrderRechargeBackVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayOrderRechargeBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayOrderRechargeController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/20 10:00
 */
@RestController
@Slf4j
@RequestMapping("/payRecharge")
@Api(tags = "财务管理-充值订单")
public class PayOrderRechargeController extends BaseAdminController {

    @Resource
    private PayOrderRechargeBusiness payOrderRechargeBusiness;


    @ApiOperation("查询-线下充值订单列表-分页")
    @GetMapping("/v1/offline/listPage")
    public ResultInfo<PageInfo<OfflinePayOrderRechargeBackVO>> offlineListPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("订单状态 1-处理中  2-成功  3-失败 4-取消") @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
            @ApiParam("开始时间") @RequestParam(value = "startDate", required = false) String startDate,
            @ApiParam("结束时间") @RequestParam(value = "endDate", required = false) String endDate,
            @ApiParam("会员账号") @RequestParam(value = "account", required = false) String account,
            @ApiParam("订单号") @RequestParam(value = "orderNo", required = false) String orderNo) {
        LoginUser user = getLoginAdmin();
        return ResultInfo.ok(payOrderRechargeBusiness.offlineListPage(pageNum, pageSize, orderStatus, account, orderNo, startDate, endDate, user));
    }

    @ApiOperation("查询-线上充值订单列表-分页")
    @GetMapping("/v1/online/listPage")
    public ResultInfo<PageInfo<OnlinePayOrderRechargeBackVO>> onlineListPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("订单状态 1-处理中  2-成功  3-失败 4-取消") @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
            @ApiParam("开始时间") @RequestParam(value = "startDate", required = false) String startDate,
            @ApiParam("结束时间") @RequestParam(value = "endDate", required = false) String endDate,
            @ApiParam("支付商id") @RequestParam(value = "providerId", required = false) String providerId,
            @ApiParam("会员账号") @RequestParam(value = "account", required = false) String account,
            @ApiParam("订单号") @RequestParam(value = "orderNo", required = false) String orderNo) {
        LoginUser user = getLoginAdmin();
        return ResultInfo.ok(payOrderRechargeBusiness.onlineListPage(pageNum, pageSize, orderStatus, account, orderNo, providerId, startDate, endDate, user));
    }

    @Log("线下充值处理")
    @ApiOperation("线下充值处理")
    @PostMapping("/offline/rechargeHandle")
    @ResponseBody
    public ResultInfo rechargeHandle(OfflineRechargeHandleReq rechargeHandleReq) {
        payOrderRechargeBusiness.rechargeHandle(rechargeHandleReq);
        return ResultInfo.ok();
    }


}
