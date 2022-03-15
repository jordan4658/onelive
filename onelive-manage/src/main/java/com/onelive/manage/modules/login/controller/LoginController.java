package com.onelive.manage.modules.login.controller;


import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.login.BackLoginReq;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.login.business.LoginBusiness;
import com.onelive.manage.utils.other.LogUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

;

/**
 * 登录相关
 */
@RestController
@RequestMapping("/manage")
@Slf4j
@Api(tags = "后台登录-API")
public class LoginController extends BaseAdminController {


    @Resource
    private LoginBusiness loginBusiness;

    @AllowAccess
    @RequestMapping(name = "登录", value = "/login", method = RequestMethod.POST)
    @ApiOperation("登录")
    public ResultInfo<LoginUser> login(@RequestBody BackLoginReq req) {
        LoginUser org = loginBusiness.doLogin(req, resolveIp(request), getServerIp(), null);
        LogUtils.logUserModifyOperates(getClass().getName() + ".login", req, null);
        return ResultInfo.ok(org);
    }

}
