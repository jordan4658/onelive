package com.onelive.job.scheduled.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onelive.job.scheduled.agent.business.AgentRebatesBusiness;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * @author mao
 * 		代理的返点，根据注单统计下级的投注金额
 *
 */
@Component
public class AgentRebatesHandlerJob {

    @Autowired
    private AgentRebatesBusiness agentRebatesBusiness;

    /**
     * 	代理的返点，根据注单统计下级的投注金额给代理用户加钱
     *
     * @return
     */
    @XxlJob("agentRebatesHandler")
    public ReturnT<String> agentRebates(String param) {
    	XxlJobLogger.log("===AgentRebatesHandlerJob 代理的返点，根据注单统计下级的投注金额给代理用户加钱===");
    	agentRebatesBusiness.startRebates();
        return ReturnT.SUCCESS;
    }

}
