package com.onelive.manage.modules.mem.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.mem.*;
import com.onelive.common.model.vo.mem.*;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.mem.business.MemUserListBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户列表
 */
@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户管理-用户列表")
@Slf4j
public class MemUserListController extends BaseAdminController {

    @Resource
    private MemUserListBusiness memUserBusiness;

    @PostMapping(value = "/v1/getList")
    @ApiOperation("用户列表")
    public ResultInfo<PageInfo<MemUserListVO>> getUserList(@RequestBody MemUserListReq req) {
        return memUserBusiness.getList(req);
    }


    @PostMapping(value = "/v1/getSelectUserList")
    @ApiOperation("查询用于选择的用户列表")
    public ResultInfo<PageInfo<MemUserSelectListVO>> getSelectUserList(@RequestBody MemUserSelectReq req) {
        return ResultInfo.ok(memUserBusiness.getSelectUserList(req));
    }

    @GetMapping(value = "/v1/getUser")
    @ApiOperation("用户信息")
    public ResultInfo<MemUserVO> getUser(Long id) {
        return memUserBusiness.getUserVoById(id);
    }


    @Log("后台批量封停/解封用户")
    @PostMapping("/v1/updateFrozenUsers")
    @ApiOperation("后台批量封停/解封用户")
    public ResultInfo<Boolean> updateFrozenUsers(@RequestBody MemUserUpdateFrozenReq req) {
        return memUserBusiness.updateFrozenUsers(req);
    }


    @Log("后台新增用户")
    @PostMapping("/v1/addUser")
    @ApiOperation("后台新增用户")
    public ResultInfo<Boolean> addUser(@RequestBody MemUserAddReq req) {
        LoginUser loginUser = getLoginAdmin();
        return memUserBusiness.addUser(req,loginUser);
    }


    @Log("后台编辑用户")
    @PostMapping("/v1/updateUser")
    @ApiOperation("后台编辑用户")
    public ResultInfo<Boolean> updateUser(@RequestBody  MemUserUpdateReq req) {
        LoginUser loginUser = getLoginAdmin();
        return memUserBusiness.updateUser(req,loginUser);
    }

    @Log("后台设置用户状态")
    @PostMapping("/v1/setUserStatus")
    @ApiOperation("后台设置用户状态")
    public ResultInfo<Boolean> setUserStatus(@RequestBody MemUserSetStatusReq req) {
        LoginUser loginUser = getLoginAdmin();
        return memUserBusiness.setUserStatus(req,loginUser);
    }

    @GetMapping(value = "/v1/getUserSet")
    @ApiOperation("用户状态查询")
    public ResultInfo<MemUserSetVO> getUserSet(Long userId) {
        return ResultInfo.ok(memUserBusiness.getUserSet(userId));
    }

    @PostMapping(value = "/v1/queryLoginInfo")
    @ApiOperation("用户登陆信息查询")
    public ResultInfo<PageInfo<MemUserLoginVO>> queryLoginInfo(@RequestBody MemUserLoginReq req) {
        return ResultInfo.ok(memUserBusiness.queryLoginInfo(req));
    }

    @Log("后台强制用户下线")
    @PostMapping("/v1/forceOffline")
    @ApiOperation("后台强制用户下线")
    public ResultInfo<Boolean> forceOffline(Long userId) {
        return memUserBusiness.forceOffline(userId);
    }

}
