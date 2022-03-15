package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayThreeProviderAddBackReq;
import com.onelive.common.model.req.pay.PayThreeProviderUpBackReq;
import com.onelive.common.model.req.pay.PayThreeProviderUpStatusBackReq;
import com.onelive.common.model.vo.pay.PayThreeProviderBackVO;
import com.onelive.common.model.vo.pay.PayThreeProviderSelectVO;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayThreeProviderBusiness;
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
 * @ClassName: PayThreeProviderController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/17 12:07
 */
@Slf4j
@RestController
@RequestMapping("/payProvider")
@Api(tags = "财务管理-支付商管理")
public class PayThreeProviderController extends BaseAdminController {

    @Resource
    private PayThreeProviderBusiness payThreeProviderBusiness;

    @ApiOperation("查询-支付商列表-分页")
    @GetMapping("/v1/listPage")
    public ResultInfo<PageInfo<PayThreeProviderBackVO>> listPage(
            @ApiParam("页码-默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数-默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("支付类型：1-线上，2-线下") @RequestParam(value = "providerType", required = false) Integer providerType,
            @ApiParam("支付类型名称") @RequestParam(value = "providerName", required = false) String providerName) {
        LoginUser user = getLoginAdmin();
        return ResultInfo.ok(payThreeProviderBusiness.listPage(user, pageNum, pageSize, providerName, providerType));
    }

    @ApiOperation("查询-支付商列表")
    @GetMapping("/v1/selectList")
    public ResultInfo<List<PayThreeProviderSelectVO>> selectList(@ApiParam("支付类型：1-线上，2-线下") @RequestParam(value = "providerType", required = false) Integer providerType) {
        return ResultInfo.ok(payThreeProviderBusiness.selectList(providerType));
    }

    @ApiOperation("查询-银行列表")
    @GetMapping("/v1/bank/list")
    public ResultInfo<List<SysBusParameter>> bankList() {
        return ResultInfo.ok(payThreeProviderBusiness.bankList());
    }

    @Log("新增-支付商")
    @ApiOperation("新增-支付商")
    @PostMapping("/v1/add")
    public ResultInfo add(@ApiParam("新增支付方式信息") @RequestBody PayThreeProviderAddBackReq payThreeProviderAddBackReq) {
        LoginUser user = getLoginAdmin();
        payThreeProviderBusiness.add(payThreeProviderAddBackReq, user);
        return ResultInfo.ok();
    }

    @Log("更新-支付商")
    @ApiOperation("更新-支付商")
    @PostMapping("/v1/update")
    public ResultInfo update(@ApiParam("更新支付商信息") @RequestBody PayThreeProviderUpBackReq payThreeProviderUpBackReq) {
        LoginUser user = getLoginAdmin();
        payThreeProviderBusiness.update(payThreeProviderUpBackReq, user);
        return ResultInfo.ok();
    }

    @Log("禁用-启用支付商")
    @ApiOperation("禁用-启用支付商")
    @PostMapping("/v1/updateEnable")
    public ResultInfo updateEnable(@ApiParam("禁用-启用支付商信息") @RequestBody PayThreeProviderUpStatusBackReq payThreeProviderUpStatusBackReq) {
        LoginUser user = getLoginAdmin();
        payThreeProviderBusiness.updateEnable(payThreeProviderUpStatusBackReq, user);
        return ResultInfo.ok();
    }
}
