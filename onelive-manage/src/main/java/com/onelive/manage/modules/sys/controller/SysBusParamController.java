package com.onelive.manage.modules.sys.controller;


import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysBusParameterQueryReq;
import com.onelive.common.model.req.sys.SysBusParameterReq;
import com.onelive.common.model.req.sys.SysBusParameterStatusReq;
import com.onelive.common.model.req.sys.SysBusParameterUpdateReq;
import com.onelive.common.model.vo.sys.SysBusParameterListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysBusParamBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lorenzo
 * @Description: 业务参数
 * @date 2021/4/6
 */
@RestController
@RequestMapping(value = "/busParam")
@Api(tags = "系统管理-业务参数")
@Slf4j
public class SysBusParamController extends BaseAdminController {

    @Resource
    private SysBusParamBusiness sysBusParamBusiness;

    @GetMapping("/v1/getList")
    @ApiOperation("业务参数列表")
    public ResultInfo<PageInfo<SysBusParameterListVO>> getList(@ModelAttribute SysBusParameterQueryReq query) {
        PageInfo<SysBusParameterListVO> list = sysBusParamBusiness.getList(query);
        return ResultInfo.ok(list);
    }

    @Log("添加业务参数")
    @PostMapping("/v1/addSysBusParameter")
    @ApiOperation("添加业务参数")
    public ResultInfo addSysBusParameter(@RequestBody SysBusParameterReq req) {
        LoginUser admin = getLoginAdmin();
        sysBusParamBusiness.addSysBusParameter(req, admin);
        return ResultInfo.ok();
    }

    @Log("编辑业务参数")
    @PostMapping("/v1/updateSysBusParameter")
    @ApiOperation("编辑业务参数")
    public ResultInfo updateSysBusParameter(@RequestBody SysBusParameterUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        sysBusParamBusiness.updateSysBusParameter(req, admin);
        return ResultInfo.ok();
    }

    @Log("切换业务参数状态")
    @GetMapping("/v1/switchStatus")
    @ApiOperation("切换业务参数状态")
    public ResultInfo switchStatus(@ModelAttribute SysBusParameterStatusReq req) {
        LoginUser admin = getLoginAdmin();
        sysBusParamBusiness.switchSysBusParameterStatus(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除业务参数")
    @PostMapping("/v1/delete")
    @ApiOperation("删除业务参数")
    public ResultInfo switchStatus(@RequestBody Long id) {
        LoginUser admin = getLoginAdmin();
        sysBusParamBusiness.deleteParam(id, admin.getAccLogin());
        return ResultInfo.ok();
    }

}
