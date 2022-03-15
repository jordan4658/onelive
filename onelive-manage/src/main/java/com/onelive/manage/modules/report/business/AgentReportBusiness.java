package com.onelive.manage.modules.report.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.report.AgentReportListReq;
import com.onelive.common.model.vo.report.AgentReportListVo;
import com.onelive.manage.service.agent.AgentInviteRecordService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AgentReportBusiness {

    @Resource
    private AgentInviteRecordService agentInviteRecordService;

    /**
     * 查询代理报表数据
     * @param param
     * @return
     */
    public PageInfo<AgentReportListVo> getReportList(AgentReportListReq param) {
        return agentInviteRecordService.getReportList(param);
    }
}
