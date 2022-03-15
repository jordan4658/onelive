package com.onelive.manage.service.agent;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.agent.AgentForbiddenProfitReq;
import com.onelive.common.model.vo.agent.AgentForbiddenProfitVo;
import com.onelive.common.mybatis.entity.AgentForbiddenProfit;

import java.util.List;

/**
 * <p>
 *  服务类 禁止代理返点
 * </p>
 *
 * @author ${author}
 * @since 2021-11-02
 */
public interface AgentForbiddenProfitService extends IService<AgentForbiddenProfit> {

    PageInfo<AgentForbiddenProfitVo> getList(AgentForbiddenProfitReq param);

    void removeByUserIds(List<Long> userIds);
}
