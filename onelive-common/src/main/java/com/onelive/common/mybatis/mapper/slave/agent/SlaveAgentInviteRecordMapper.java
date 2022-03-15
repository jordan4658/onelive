package com.onelive.common.mybatis.mapper.slave.agent;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.agent.AgentInviteRecordDto;
import com.onelive.common.model.req.report.AgentReportListReq;
import com.onelive.common.model.vo.report.AgentReportListVo;
import com.onelive.common.mybatis.entity.AgentInviteRecord;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-16
 */
public interface SlaveAgentInviteRecordMapper extends BaseMapper<AgentInviteRecord> {

    /**
     * 查询代理报表数据
     * @param param
     * @return
     */
    List<AgentReportListVo> getReportList(AgentReportListReq param);
    
    /**
     * 	上一天,下级有投注的上级代理数量
     * 
     * @return
     */
    int getYesterdayAgentUserBetCount();

	/**
	 * 	上一天代理下用户的投注额
	 * @return
	 */
	List<AgentInviteRecordDto> getAgentUserYesterdaBetAmount();
    
    
    
}
