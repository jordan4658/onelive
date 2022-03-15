package com.onelive.api.modules.login.controller;


import com.alibaba.fastjson.JSONObject;
import com.onelive.api.modules.login.business.LoginBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.req.login.*;
import com.onelive.common.model.vo.common.BooleanVO;
import com.onelive.common.model.vo.login.AppLoginTokenVo;
import com.onelive.common.model.vo.login.UserAreaVo;
import com.onelive.common.utils.Login.LoginInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 登录
 */
@RestController
@RequestMapping("/login")
@Api(tags = "注册登录接口")
@Slf4j
public class LoginController extends BaseController {

    @Resource
    private LoginBusiness loginBusiness;

    @ApiOperation("普通会员注册登陆")
    @PostMapping(value = {"/app/v1/registAndLogin", "/pc/v1/registAndLogin"})
    @AllowAccess
    public ResultInfo<AppLoginTokenVo> registAndLogin(@RequestBody(required = false) AppRegistAndLoginReq req) {
        try {
            return loginBusiness.registAndLogin(req);
        } catch (BusinessException e) {
            log.error("{}.register,失败:{},params:{}", getClass().getName(), e.getMessage(), JSONObject.toJSONString(req), e);
            return ResultInfo.getInstance(e.getStatusCode());
        } catch (Exception e) {
            log.error("{}.register,出错:{},params:{}", getClass().getName(), e.getMessage(), JSONObject.toJSONString(req), e);
        }
        return ResultInfo.error();
    }

    @ApiOperation("普通会员注册")
    @PostMapping(value = {"/app/v1/register", "/pc/v1/register"})
    @AllowAccess
    @ApiIgnore
    public ResultInfo<AppLoginTokenVo> register(@RequestBody(required = false) RegisterReq req) {
        try {
            return loginBusiness.register(req);
        } catch (BusinessException e) {
            log.error("{}.register,失败:{},params:{}", getClass().getName(), e.getMessage(), JSONObject.toJSONString(req), e);
            return ResultInfo.getInstance(e.getStatusCode());
        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage();
            if (StringUtils.isNotBlank(message) && message.indexOf("Duplicate") != -1) {
                log.error("{}.register,唯一索引报错:{},params:{}", getClass().getName(), message, JSONObject.toJSONString(req), e);
                return ResultInfo.getInstance(StatusCode.REGISTER_EXISTS_PHONE);
            }
            log.error("{}.register,出错:{},params:{}", getClass().getName(), message, JSONObject.toJSONString(req), e);
        } catch (Exception e) {
            log.error("{}.register,出错:{},params:{}", getClass().getName(), e.getMessage(), JSONObject.toJSONString(req), e);
        }
        return ResultInfo.error();
    }

    @ApiOperation("普通会员登录")
    @AllowAccess
    @ApiIgnore
    @PostMapping(value = {"/app/v1/login", "/pc/v1/login"})
    public ResultInfo<AppLoginTokenVo> login(@RequestBody(required = false)AppLoginReq req) {
        return loginBusiness.login(req);
    }

    @ApiOperation("游客注册登录")
    @AllowAccess
    @PostMapping(value = {"/app/v1/visitorLogin", "/pc/v1/visitorLogin"})
    public ResultInfo<AppLoginTokenVo> visitorLogin() {
        return loginBusiness.visitorLogin();
    }

    @ApiOperation("游客绑定手机号")
    @PostMapping(value = {"/app/v1/visitor/bindPhone", "/pc/v1/visitor/bindPhone"})
    public ResultInfo<Boolean> bindPhone(@RequestBody(required = false) BindPhoneReq req) throws Exception{
        return loginBusiness.bindPhone(req);
    }

    @ApiOperation("判断游客已绑定手机号，不能走游客登录")
    @AllowAccess
    @PostMapping(value = {"/app/v1/visitor/check", "/pc/v1/visitor/check"})
    public ResultInfo<Boolean> checkVisitor() throws Exception{
        return loginBusiness.checkVisitor();
    }

    @ApiOperation("普通会员退出登录")
    @PostMapping(value = {"/app/v1/logout", "/pc/v1/logout"})
    public ResultInfo<String> logout() {
        String token = this.getToken();
        AppLoginUser user = this.getLoginUserAPP();
        if (user != null) {
            loginBusiness.logout(user.getAccno(), token);
        }
        return ResultInfo.ok();
    }

    @ApiOperation(value = "普通会员找回密码接口-重置密码")
    @PostMapping(value = {"/app/v1/resetPassword", "/pc/v1/resetPassword"})
    @AllowAccess
    @ApiIgnore
    public ResultInfo<String> resetPassword(@RequestBody(required = false) ResetPasswordReq req) throws Exception {
        return loginBusiness.resetPassword(req);
    }

    @ApiOperation("普通会员是否存在用户名")
    @AllowAccess
    @PostMapping(value = {"/app/v1/isExistsAccount", "/pc/v1/isExistsAccount"})
    public ResultInfo<Boolean> isExistsAccount(@RequestBody(required = false) ApiCheckAccountReq req) {
        return ResultInfo.ok(loginBusiness.isExistsAccount(req));
    }

    @ApiOperation("根据IP查询用户地区")
    @AllowAccess
    @PostMapping(value = {"/app/v1/checkArea", "/pc/v1/checkArea"})
    public ResultInfo<UserAreaVo> checkArea(HttpServletRequest request) {
        return ResultInfo.ok(loginBusiness.checkArea(request));
    }


    @ApiOperation("游客选择地区")
    @AllowAccess
    @PostMapping(value = {"/app/v1/visitor/selectArea", "/pc/v1/visitor/selectArea"})
    public ResultInfo<Boolean> selectArea(@RequestBody AppVistorChangeAreaReq req,HttpServletRequest request) throws Exception{
        return loginBusiness.selectArea(req,request);
    }

    @ApiOperation("根据设备ID查询是否有对应的账号存在")
    @AllowAccess
    @PostMapping(value = {"/app/v1/checkAccountByDeviceId"})
    public ResultInfo<BooleanVO> checkAccountByDeviceId() throws Exception{
        return ResultInfo.ok(loginBusiness.checkAccountByDeviceId());
    }

    @ApiOperation("查询登陆用户的countryCode")
    @PostMapping(value = {"/app/v1/checkCountryCode"})
    public ResultInfo<String> checkCountryCode() throws Exception{
        return ResultInfo.ok(LoginInfoUtil.getCountryCode());
    }

}
