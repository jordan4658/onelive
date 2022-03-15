package com.onelive.manage.modules.permission.controller;


import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.*;

import com.onelive.common.model.vo.sys.*;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.permission.business.PermissionBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lorenzo
 * @Description: 权限设置控制器
 * @date 2021/3/31
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "权限管理-权限设置")
public class PermissionController extends BaseAdminController {

    @Resource
    private PermissionBusiness permissionBusiness;

    /**********************************************=== 系统用户相关 ==*********************************************************/

    @GetMapping("/v1/getSysUserList")
    @ApiOperation("获取系统用户列表（员工列表）")
    public ResultInfo<PageInfo<SysUserListVO>> getSysUserList(@ModelAttribute SysUserQueryReq req) {
        PageInfo<SysUserListVO> sysUserList = permissionBusiness.getSysUserList(req);
        return ResultInfo.ok(sysUserList);
    }

    @Log("保存系统用户")
    @PostMapping("/v1/saveSysUser")
    @ApiOperation("保存系统用户（员工）")
    public ResultInfo saveSysUser(@RequestBody SysUserReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.saveSysUser(req, admin);
        return ResultInfo.ok();
    }

    @Log("修改系统用户")
    @PostMapping("/v1/updateSysUser")
    @ApiOperation("修改系统用户（员工）")
    public ResultInfo updateSysUser(@RequestBody SysUserUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.updateSysUser(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除系统用户")
    @PostMapping("/v1/deleteSysUser")
    @ApiOperation("删除系统用户")
    public ResultInfo deleteSysUser(@ApiParam("用户id") @RequestParam Long userId) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.deleteSysUser(userId, admin);
        return ResultInfo.ok();
    }


    @GetMapping("/v1/getSysUserDetail")
    @ApiOperation("查询系统用户详细信息")
    public ResultInfo<SysUserDetailVO> getSysUserDetail(@RequestParam Long userId) {
        LoginUser admin = getLoginAdmin();
        SysUserDetailVO sysUserDetail = permissionBusiness.getSysUserDetail(userId, admin);
        return ResultInfo.ok(sysUserDetail);
    }

    @Log("修改系统用户状态")
    @PostMapping("/v1/updateSysUserStatus")
    @ApiOperation("修改系统用户状态")
    public ResultInfo updateStatus(@RequestBody SysUserStatusReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.updateStatus(req, admin);
        return ResultInfo.ok();
    }

    /**********************************************=== 系统用户相关 end ==*********************************************************/

    /**********************************************=== 系统角色相关 ==*********************************************************/

    @GetMapping("/v1/getRoleList")
    @ApiOperation("角色列表分页")
    public ResultInfo<PageInfo<SysRoleListVO>> getRoleList(@ModelAttribute SysRoleQueryReq req) {
        return ResultInfo.ok(permissionBusiness.getRoleList(req));
    }


    @GetMapping("/v1/getRoleUserList")
    @ApiOperation("查询角色所属的用户列表")
    public ResultInfo<SysRoleUserListVO> getRoleUserList(@ApiParam("角色的id") @RequestParam Long roleId) {
        SysRoleUserListVO roleUserList = permissionBusiness.getRoleUserList(roleId);
        return ResultInfo.ok(roleUserList);
    }

    @Log("新增角色")
    @PostMapping("/v1/saveRole")
    @ApiOperation("新增角色")
    public ResultInfo saveRole(@RequestBody SysRoleReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.saveRole(req, admin);
        return ResultInfo.ok();
    }

    @Log("编辑角色")
    @PostMapping("/v1/updateRole")
    @ApiOperation("编辑角色")
    public ResultInfo updateRole(@RequestBody SysRoleUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.updateRole(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除角色")
    @PostMapping("/v1/deleteRole")
    @ApiOperation("删除角色")
    public ResultInfo deleteRole(@ApiParam("角色的id") @RequestParam Long roleId) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.deleteRole(roleId, admin);
        return ResultInfo.ok();
    }


    @GetMapping("/v1/detailRoleFunction")
    @ApiOperation("查询角色模块权限")
    public ResultInfo<SysRoleDetailVO> detailRoleFunction(@ApiParam("角色的id") @RequestParam Long roleId) {
        SysRoleDetailVO vo = permissionBusiness.detailRoleFunction(roleId);
        return ResultInfo.ok(vo);
    }

    @Log("配置权限,角色功能关系设置")
    @PostMapping("/v1/setRoleFunction")
    @ApiOperation("配置权限,角色功能关系设置")
    public ResultInfo setRoleFunction(@RequestBody RoleFunctionReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.setRoleFunction(req, admin);
        return ResultInfo.ok();
    }

    @Log("新增用户与角色的关系")
    @PostMapping("/v1/addUserRole")
    @ApiOperation("新增用户与角色的关系")
    public ResultInfo addUserRole(@RequestBody SysUserRoleReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.addUserRole(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除用户与角色的关系")
    @GetMapping("/v1/delUserRole")
    @ApiOperation("删除用户与角色的关系")
    public ResultInfo delUserRole(Long userId) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.delUserRole(userId, admin);
        return ResultInfo.ok();
    }

    /**********************************************=== 系统角色相关end ==*********************************************************/

    /**********************************************=== 系统功能相关 ==*********************************************************/

    @GetMapping("/v1/getSysFunctionTree")
    @ApiOperation("获取模块功能列表")
    public ResultInfo<List<SysFunctionVO>> getSysFunctionTree() {
        List<SysFunctionVO> sysFunctionTree = permissionBusiness.getSysFunctionTree();
        return ResultInfo.ok(sysFunctionTree);
    }

    @GetMapping("/v1/getSelectFunctionTree")
    @ApiOperation("获取功能模块选择树")
    public ResultInfo<List<SysFunctionVO>> getSelectFunctionTree(@RequestParam Long funId) {
        List<SysFunctionVO> tree = permissionBusiness.getSelectFunctionTree(funId);
        return ResultInfo.ok(tree);
    }

    @Log("功能新增")
    @PostMapping("/v1/saveFunction")
    @ApiOperation("功能新增")
    public ResultInfo saveFunction(@RequestBody SysFunctionReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.saveFunction(req, admin);
        return ResultInfo.ok();
    }

    @Log("修改功能")
    @PostMapping("/v1/updateFunction")
    @ApiOperation("修改功能")
    public ResultInfo updateFunction(@RequestBody SysFunctionUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.updateFunction(req, admin);
        return ResultInfo.ok();
    }

    @Log("切换功能状态")
    @PostMapping("/v1/switchFunctionStatus")
    @ApiOperation("切换功能状态")
    public ResultInfo switchFunctionStatus(@RequestBody SysFunctionStatusReq req) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.switchFunctionStatus(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除功能")
    @GetMapping("/v1/delFunction")
    @ApiOperation("删除功能")
    public ResultInfo delFunction(@RequestParam Long funcId) {
        LoginUser admin = getLoginAdmin();
        permissionBusiness.delFunction(funcId, admin);
        return ResultInfo.ok();
    }

    /**********************************************=== 系统功能相关 end ==*********************************************************/
}
