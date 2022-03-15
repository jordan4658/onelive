package com.onelive.manage.modules.agent.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.agent.*;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.vo.agent.AgentInviteCodeInfoVo;
import com.onelive.common.model.vo.agent.AgentInviteCodeVo;
import com.onelive.common.model.vo.agent.SearchUserVo;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.agent.business.AgentInvitCodeBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/agent")
@Api(tags = "用户管理-邀请码列表")
@Slf4j
public class AgentInvitCodeController {
    @Resource
    private AgentInvitCodeBusiness invitCodeBusiness;

    @GetMapping("/v1/getInviteCodeList")
    @ApiOperation("查询邀请码列表")
    public ResultInfo<PageInfo<AgentInviteCodeVo>> getList(AgentInviteCodeReq param) {
        return invitCodeBusiness.getList(param);
    }
    @GetMapping("/v1/searchUser")
    @ApiOperation("查询用户信息")
    public ResultInfo<SearchUserVo> searchUser(SearchUserReq req) {
        return invitCodeBusiness.searchUser(req);
    }

    @Log("后台添加邀请码")
    @PostMapping("/v1/addInviteCode")
    @ApiOperation("后台添加邀请码")
    public ResultInfo<Boolean> addInviteCode(AgentInviteCodeAddReq req) {
        return invitCodeBusiness.addInviteCode(req);
    }

    @Log("后台批量删除邀请码")
    @PostMapping("/v1/deleteInviteCode")
    @ApiOperation("后台批量删除邀请码")
    public ResultInfo<Boolean> deleteInviteCode(@RequestBody AgentInviteCodeDeleteReq req) {
        return invitCodeBusiness.delInviteCode(req);
    }


    @Log("查询邀请码信息")
    @PostMapping("/v1/getInviteCodeInfo")
    @ApiOperation("查询邀请码信息")
    public ResultInfo<AgentInviteCodeInfoVo> getInviteCodeInfo(@RequestBody LongIdReq req) {
        return ResultInfo.ok(invitCodeBusiness.getInviteCodeInfo(req));
    }

    @Log("更改邀请码状态")
    @PostMapping("/v1/changeInviteCodeStatus")
    @ApiOperation("更改邀请码状态")
    public ResultInfo<Boolean> changeInviteCodeStatus(@RequestBody AgentInviteCodeChangeStatusReq req) {
        return invitCodeBusiness.changeInviteCodeStatus(req);
    }
}
