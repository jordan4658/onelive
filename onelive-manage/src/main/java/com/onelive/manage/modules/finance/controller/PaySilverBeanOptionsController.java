package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.*;
import com.onelive.common.model.vo.pay.PaySilverBeanOptionsByIdVO;
import com.onelive.common.model.vo.pay.PaySilverBeanOptionsVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PaySilverBeanOptionsBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/silverBeanOptions")
@Api(tags = "财务管理-兑换快捷选项管理")
public class PaySilverBeanOptionsController extends BaseAdminController {

    @Resource
    private PaySilverBeanOptionsBusiness paySilverBeanOptionsBusiness;

    @ApiOperation("查询-兑换快捷选项列表")
    @GetMapping("/v1/listPage")
    public ResultInfo<PageInfo<PaySilverBeanOptionsVO>> listPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("状态：0-禁用 、1-启用") @RequestParam(value = "isEnable", required = false) Boolean isEnable) {
        return ResultInfo.ok(paySilverBeanOptionsBusiness.listPage(pageNum, pageSize, isEnable));
    }


    @ApiOperation("根据快捷选项ID查询对应的信息")
    @GetMapping("/v1/getById")
    public ResultInfo<PaySilverBeanOptionsByIdVO> getById(
            @ApiParam(value = "兑换快捷选项ID",required = true) @RequestParam(value = "id") Long id) {
        return ResultInfo.ok(paySilverBeanOptionsBusiness.getById(id));
    }


    @Log("新增-兑换快捷选项")
    @ApiOperation("新增-兑换快捷选项")
    @PostMapping("/v1/add")
    public ResultInfo add(@ApiParam("新增兑换快捷选项信息") @RequestBody PayWithdrawalOptionsAddReq req) {
        LoginUser user = getLoginAdmin();
        paySilverBeanOptionsBusiness.add(req, user);
        return ResultInfo.ok();
    }

    @Log("更新-兑换快捷选项")
    @ApiOperation("更新-兑换快捷选项")
    @PostMapping("/v1/update")
    public ResultInfo update(@ApiParam("更新兑换快捷选项信息") @RequestBody PayWithdrawalOptionsUpdateReq req) {
        LoginUser user = getLoginAdmin();
        paySilverBeanOptionsBusiness.update(req, user);
        return ResultInfo.ok();
    }

    @Log("禁用-启用兑换快捷选项")
    @ApiOperation("禁用-启用兑换快捷选项")
    @PostMapping("/v1/updateEnable")
    public ResultInfo updateEnable(@ApiParam("禁用-启用兑换快捷选项信息") @RequestBody PayWithdrawalOptionsIsEnableReq req) {
        LoginUser user = getLoginAdmin();
        paySilverBeanOptionsBusiness.updateEnable(req, user);
        return ResultInfo.ok();
    }

    @Log("删除-兑换快捷选项")
    @ApiOperation("删除-兑换快捷选项")
    @PostMapping("/v1/delete")
    public ResultInfo delete(@ApiParam("删除-兑换快捷选项信息") @RequestBody PayWithdrawalOptionsDeleteReq req) {
        LoginUser user = getLoginAdmin();
        paySilverBeanOptionsBusiness.delete(req, user);
        return ResultInfo.ok();
    }

}
