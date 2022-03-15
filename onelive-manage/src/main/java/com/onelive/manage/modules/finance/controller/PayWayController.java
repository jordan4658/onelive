package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayWayBackAddReq;
import com.onelive.common.model.req.pay.PayWayBackUpReq;
import com.onelive.common.model.req.pay.PayWayBackUpStatusReq;
import com.onelive.common.model.vo.pay.PayTypeSelectVO;
import com.onelive.common.model.vo.pay.PayWayBackVO;
import com.onelive.common.model.vo.pay.PayWaySelectVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayWayBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWayController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 16:23
 */
@Slf4j
@RestController
@RequestMapping("/payWay")
@Api(tags = "财务管理-支付方式管理")
public class PayWayController extends BaseAdminController {


    @Resource
    private PayWayBusiness payWayBusiness;

    @ApiOperation("查询-支付方式列表")
    @GetMapping("/v1/listPage")
    public ResultInfo<PageInfo<PayWayBackVO>> listPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("支付方式名称") @RequestParam(value = "payWayName", required = false) String payWayName,
            @ApiParam(value = "国家编码") @RequestParam(value = "countryCode", required = false) String countryCode,
            @ApiParam("支付类型code：1-支付宝、2-微信、3-银联") @RequestParam(value = "payTypeCode", required = false) String payTypeCode,
            @ApiParam(value = "支付商类类型：1-线上、2-线下",required = true) @RequestParam(value = "providerType") Integer providerType,
            @ApiParam("状态：1-启用，2-禁用") @RequestParam(value = "status",required = false) Integer status) {
        LoginUser user = getLoginAdmin();
        return ResultInfo.ok(payWayBusiness.listPage(pageNum, pageSize, payWayName,countryCode, payTypeCode, status, user,providerType));
    }

    @Log("新增-支付方式")
    @ApiOperation("新增-支付方式")
    @PostMapping("/v1/add")
    public ResultInfo add(@ApiParam("新增支付方式信息") @RequestBody PayWayBackAddReq payWayBackAddReq) {
        LoginUser user = getLoginAdmin();
        payWayBusiness.add(payWayBackAddReq, user);
        return ResultInfo.ok();
    }

    @Log("更新-支付方式")
    @ApiOperation("更新-支付方式")
    @PostMapping("/v1/update")
    public ResultInfo update(@ApiParam("更新支付方式信息") @RequestBody PayWayBackUpReq payWayBackUpReq) {
        LoginUser user = getLoginAdmin();
        payWayBusiness.update(payWayBackUpReq, user);
        return ResultInfo.ok();
    }

    @Log("禁用-启用支付方式")
    @ApiOperation("禁用-启用支付方式")
    @PostMapping("/v1/updateEnable")
    public ResultInfo updateEnable(@ApiParam("禁用-启用支付方式信息") @RequestBody PayWayBackUpStatusReq payWayBackUpStatusReq) {
        LoginUser user = getLoginAdmin();
        payWayBusiness.updateEnable(payWayBackUpStatusReq, user);
        return ResultInfo.ok();
    }


    @ApiOperation("查询-支付方式下拉列表")
    @GetMapping("/v1/search")
    public ResultInfo<List<PayWaySelectVO>> select(@ApiParam("国家编码") @RequestParam() String countryCode) {
        return ResultInfo.ok( payWayBusiness.select(countryCode));
    }


}
