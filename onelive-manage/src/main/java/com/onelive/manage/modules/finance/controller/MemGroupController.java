package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.mem.*;
import com.onelive.common.model.vo.mem.*;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.MemGroupBusiness;
import com.onelive.manage.modules.mem.business.MemLevelVipBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/group")
@Api(tags = "财务管理-用户层级管理")
@Slf4j
public class MemGroupController extends BaseAdminController {

    @Resource
    private MemGroupBusiness business;

    @GetMapping(value = "/v1/getList")
    @ApiOperation("层级列表-下拉")
    public ResultInfo<List<MemUserGroupSelectVO>> getList(
            @ApiParam(value = "国家编码", required = true) @RequestParam(value = "currencyCode") String currencyCode) {
        return ResultInfo.ok(business.getList(currencyCode));
    }


    @GetMapping(value = "/v1/pageList")
    @ApiOperation("层级列表")
    public ResultInfo<PageInfo<MemUserGroupVO>> pageList(
            @ApiParam(value = "条数默认10", defaultValue = "10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam(value = "页数默认1", defaultValue = "1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "国家编码") @RequestParam(value = "currencyCode", required = false) String currencyCode) {
        return ResultInfo.ok(business.pageList(pageSize, pageNum, currencyCode));
    }

    @Log("新增层级")
    @PostMapping("/v1/add")
    @ApiOperation("新增层级")
    public ResultInfo<Boolean> add(@RequestBody MemUserGroupAddReq req) throws Exception {
        LoginUser admin = getLoginAdmin();
        return ResultInfo.ok(business.add(req, admin));
    }

    @Log("更新层级")
    @PostMapping("/v1/update")
    @ApiOperation("更新层级")
    public ResultInfo<Boolean> update(@RequestBody MemUserGroupUpReq req) throws Exception {
        LoginUser admin = getLoginAdmin();
        return ResultInfo.ok(business.update(req, admin));
    }

    @Log("删除用户层级")
    @PostMapping("/v1/delete")
    @ApiOperation("删除用户层级")
    public ResultInfo<Boolean> delete(@RequestBody MemUserGroupDlReq req) throws Exception {
        LoginUser admin = getLoginAdmin();
        return ResultInfo.ok(business.delete(req, admin));
    }

    @Log("层级列表页面-批量用户分层")
    @PostMapping(value = "/v1/userLevelsOfMigration")
    @ApiOperation("层级列表页面-批量用户分层")
    public ResultInfo<Boolean> userLevelsOfMigration(@RequestBody UserLevelsOfMigrationVO req) {
        return ResultInfo.ok(business.userLevelsOfMigration(req, getLoginAdmin()));
    }

    @PostMapping(value = "/v1/manage/MemUserGroupManageList")
    @ApiOperation("用户分层管理列表")
    public ResultInfo<PageInfo<MemUserGroupManageVO>> MemUserGroupManageList(@RequestBody MemUserGroupManageReq req) {
        return ResultInfo.ok(business.MemUserGroupManageList(req));
    }

    @Log("用户分成管理页面-批量用户分层")
    @PostMapping(value = "/v1/manage/batchUserGroupUpdate")
    @ApiOperation("用户分成管理页面-批量用户分层")
    public ResultInfo<Boolean> batchUserGroupUpdate(@RequestBody MemUserGroupManageUpReq req) {
        return ResultInfo.ok(business.batchUserGroupUpdate(req, getLoginAdmin()));
    }


}
