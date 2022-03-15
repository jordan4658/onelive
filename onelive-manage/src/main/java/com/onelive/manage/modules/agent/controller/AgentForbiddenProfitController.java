package com.onelive.manage.modules.agent.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.agent.AgentForbiddenProfitReq;
import com.onelive.common.model.req.agent.ForbiddenAgentRelieveReq;
import com.onelive.common.model.vo.agent.AgentForbiddenProfitVo;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.agent.business.AgentForbiddenProfitBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 禁止代理返点
 */
@RestController
@RequestMapping("/agent")
@Api(tags = "用户管理-禁止代理返点")
@Slf4j
public class AgentForbiddenProfitController {

    @Resource
    private AgentForbiddenProfitBusiness business;


    @GetMapping("/v1/getForbiddenList")
    @ApiOperation("查询禁止的代理列表")
    public ResultInfo<PageInfo<AgentForbiddenProfitVo>> getList(AgentForbiddenProfitReq param) {
        return business.getList(param);
    }

/*

    @Log("设置指定代理被禁止返点")
    @PostMapping("/v1/forbiddenAgent")
    @ApiOperation("设置指定代理被禁止返点")
    public ResultInfo<Boolean> forbiddenAgent(ForbiddenAgentReq req) {
        return business.forbiddenAgent(req);
    }
*/


    @Log("后台批量解禁")
    @PostMapping("/v1/relieveForbidden")
    @ApiOperation("后台批量解禁")
    public ResultInfo<Boolean> relieveForbidden(@RequestBody ForbiddenAgentRelieveReq req) {
        return business.relieveForbidden(req);
    }
}
