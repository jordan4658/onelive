package com.onelive.common.mybatis.mapper.slave.agent;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.req.agent.AgentForbiddenProfitReq;
import com.onelive.common.model.vo.agent.AgentForbiddenProfitVo;
import com.onelive.common.mybatis.entity.AgentForbiddenProfit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-02
 */
public interface SlaveAgentForbiddenProfitMapper extends BaseMapper<AgentForbiddenProfit> {

    List<AgentForbiddenProfitVo> getList(AgentForbiddenProfitReq param);

    void removeByUserIds(@Param("userIds") List<Long> userIds);
}
