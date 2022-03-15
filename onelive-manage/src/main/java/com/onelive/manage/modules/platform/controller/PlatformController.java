package com.onelive.manage.modules.platform.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platformConfig.PlatformConfigAllDto;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.platform.business.PlatformBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/platform/config")
@Api(tags = "平台配置")
public class PlatformController extends BaseAdminController {

    @Resource
    private PlatformBusiness platformBusiness;

    @PostMapping(value = "/v1/getList")
    @ApiOperation("平台配置列表")
    public ResultInfo<List<PlatformConfigAllDto>> getList() {
        return ResultInfo.ok(platformBusiness.getList());
    }
    
    @Log("平台配置配置")
    @PostMapping(value = "/v1/update")
    @ApiOperation("平台配置配置")
    public ResultInfo<Boolean> update(@RequestBody PlatformConfigAllDto platformConfigAllDto) {
        LoginUser loginUser = getLoginAdmin();
        platformBusiness.update(platformConfigAllDto, loginUser);
        return ResultInfo.ok();
    }
    

}
