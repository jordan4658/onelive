package com.onelive.manage.modules.finance.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.ConfirmWithdrawHandleReq;
import com.onelive.common.model.vo.pay.PayOrderWithdrawBackVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayOrderWithdrawBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayOrderWithdrawController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/21 15:08
 */
@Slf4j
@RestController
@RequestMapping("/payWithdraw")
@Api(tags = "财务管理-提现订单管理")
public class PayOrderWithdrawController extends BaseAdminController {

    @Resource
    private PayOrderWithdrawBusiness payOrderWithdrawBusiness;

    @ApiOperation("查询-提现订单列表-分页")
    @GetMapping("/v1/listPage")
    public ResultInfo<PageInfo<PayOrderWithdrawBackVO>> listPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("订单状态 1-处理中  2-成功  3-失败 4-取消") @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
            @ApiParam("会员账号") @RequestParam(value = "account", required = false) String account,
            @ApiParam("开始时间") @RequestParam(value = "startDate", required = false) String startDate,
            @ApiParam("结束时间") @RequestParam(value = "endDate", required = false) String endDate,
            @ApiParam("订单号") @RequestParam(value = "orderNo", required = false) String orderNo) {
        LoginUser user = getLoginAdmin();
        return ResultInfo.ok(payOrderWithdrawBusiness.listPage(pageNum, pageSize, orderStatus, account, orderNo, DateUtil.parseDate(startDate), DateUtil.parseDate(endDate), user));
    }

    @Log("用户提现处理")
    @ApiOperation("用户提现处理")
    @PostMapping("/v1/withdrawHandle")
    public ResultInfo withdrawHandle(@RequestBody ConfirmWithdrawHandleReq withdrawHandleReq) {
        LoginUser user = getLoginAdmin();
        payOrderWithdrawBusiness.withdrawHandle(withdrawHandleReq, user);
        return ResultInfo.ok();
    }

}
