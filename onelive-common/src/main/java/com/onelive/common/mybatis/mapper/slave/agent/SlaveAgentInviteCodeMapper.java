package com.onelive.common.mybatis.mapper.slave.agent;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.req.agent.AgentInviteCodeReq;
import com.onelive.common.model.vo.agent.AgentInviteCodeVo;
import com.onelive.common.mybatis.entity.AgentInviteCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-01
 */
public interface SlaveAgentInviteCodeMapper extends BaseMapper<AgentInviteCode> {

    List<AgentInviteCodeVo> getList(AgentInviteCodeReq param);

    void updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") Boolean status);
}
