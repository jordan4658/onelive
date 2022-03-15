package com.onelive.job.scheduled.agent.business;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.onelive.job.service.agent.AgentRebatesService;
import com.xxl.job.core.log.XxlJobLogger;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AgentRebatesBusiness {

	@Resource
	private AgentRebatesService agentRebatesService;

	public void startRebates() {
		agentRebatesService.agentRebatesDay();
		XxlJobLogger.log("======代理返点job结束======");
	}

}
