package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayShortcutOptionsAddReq;
import com.onelive.common.model.req.pay.PayShortcutOptionsDeleteReq;
import com.onelive.common.model.req.pay.PayShortcutOptionsIsEnableReq;
import com.onelive.common.model.req.pay.PayShortcutOptionsUpdateReq;
import com.onelive.common.model.vo.pay.PayShortcutOptionsByIdVO;
import com.onelive.common.model.vo.pay.PayShortcutOptionsVO;
import com.onelive.common.model.vo.pay.UnitAndExChangerVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayShortcutOptionsBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/shortcutOption")
@Api(tags = "财务管理-支付快捷选项管理")
public class PayShortcutOptionsController extends BaseAdminController {

    @Resource
    private PayShortcutOptionsBusiness payShortcutOptionsBusiness;

    @ApiOperation("查询-支付快捷选项列表")
    @GetMapping("/v1/listPage")
    public ResultInfo<PageInfo<PayShortcutOptionsVO>> listPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("支付方式名称") @RequestParam(value = "payWayId", required = false) Long payWayId,
            @ApiParam(value = "国家编码", required = true) @RequestParam(value = "countryCode") String countryCode,
            @ApiParam("状态：0-禁用 、1-启用") @RequestParam(value = "isEnable", required = false) Boolean isEnable) {
        return ResultInfo.ok(payShortcutOptionsBusiness.listPage(pageNum, pageSize, payWayId, isEnable,countryCode));
    }


    @ApiOperation("根据快捷选项ID查询对应的信息")
    @GetMapping("/v1/getById")
    public ResultInfo<PayShortcutOptionsByIdVO> getById(
            @ApiParam(value = "快捷选项ID", required = true) @RequestParam(value = "id") Long id) {
        return ResultInfo.ok(payShortcutOptionsBusiness.getById(id));
    }


    @Log("新增-支付快捷选项")
    @ApiOperation("新增-支付快捷选项")
    @PostMapping("/v1/add")
    public ResultInfo add(@ApiParam("新增支付快捷选项信息") @RequestBody PayShortcutOptionsAddReq req) {
        LoginUser user = getLoginAdmin();
        payShortcutOptionsBusiness.add(req, user);
        return ResultInfo.ok();
    }

    @Log("更新-支付快捷选项")
    @ApiOperation("更新-支付快捷选项")
    @PostMapping("/v1/update")
    public ResultInfo update(@ApiParam("更新支付快捷选项信息") @RequestBody PayShortcutOptionsUpdateReq req) {
        LoginUser user = getLoginAdmin();
        payShortcutOptionsBusiness.update(req, user);
        return ResultInfo.ok();
    }

    @Log("禁用-启用支付快捷选项")
    @ApiOperation("禁用-启用支付快捷选项")
    @PostMapping("/v1/updateEnable")
    public ResultInfo updateEnable(@ApiParam("禁用-启用支付快捷选项信息") @RequestBody PayShortcutOptionsIsEnableReq req) {
        LoginUser user = getLoginAdmin();
        payShortcutOptionsBusiness.updateEnable(req, user);
        return ResultInfo.ok();
    }

    @Log("删除-支付快捷选项")
    @ApiOperation("删除-启用支付快捷选项")
    @PostMapping("/v1/delete")
    public ResultInfo delete(@ApiParam("删除-支付快捷选项信息") @RequestBody PayShortcutOptionsDeleteReq req) {
        LoginUser user = getLoginAdmin();
        payShortcutOptionsBusiness.delete(req, user);
        return ResultInfo.ok();
    }


    @ApiOperation("查币种单位和充值汇率")
    @GetMapping("/v1/getUnitAndExChanger")
    public ResultInfo<UnitAndExChangerVO> getUnitAndExChanger(@ApiParam("国家code") @RequestParam("countryCode") String countryCode) {
        return ResultInfo.ok(payShortcutOptionsBusiness.getUnitAndExChanger(countryCode));
    }


}
