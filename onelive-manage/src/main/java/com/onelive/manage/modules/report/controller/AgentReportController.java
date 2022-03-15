package com.onelive.manage.modules.report.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.report.AgentReportListReq;
import com.onelive.common.model.vo.report.AgentReportListVo;
import com.onelive.manage.modules.report.business.AgentReportBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/report")
@Api(tags = "报表系统-代理报表")
@Slf4j
public class AgentReportController {

    @Resource
    private AgentReportBusiness agentReportBusiness;

    @GetMapping("/v1/getReportList")
    @ApiOperation("查询代表报表数据")
    public ResultInfo<PageInfo<AgentReportListVo>> getReportList(AgentReportListReq param) {

        return ResultInfo.ok(agentReportBusiness.getReportList(param));
    }

}
