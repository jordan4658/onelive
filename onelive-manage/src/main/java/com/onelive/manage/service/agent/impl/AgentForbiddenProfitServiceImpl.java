package com.onelive.manage.service.agent.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.agent.AgentForbiddenProfitReq;
import com.onelive.common.model.vo.agent.AgentForbiddenProfitVo;
import com.onelive.common.mybatis.entity.AgentForbiddenProfit;
import com.onelive.common.mybatis.mapper.master.agent.AgentForbiddenProfitMapper;
import com.onelive.common.mybatis.mapper.slave.agent.SlaveAgentForbiddenProfitMapper;
import com.onelive.manage.service.agent.AgentForbiddenProfitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-02
 */
@Service
public class AgentForbiddenProfitServiceImpl extends ServiceImpl<AgentForbiddenProfitMapper, AgentForbiddenProfit> implements AgentForbiddenProfitService {

    @Resource
    private SlaveAgentForbiddenProfitMapper slaveAgentForbiddenProfitMapper;

    @Override
    public PageInfo<AgentForbiddenProfitVo> getList(AgentForbiddenProfitReq param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<AgentForbiddenProfitVo> list = slaveAgentForbiddenProfitMapper.getList(param);
        PageInfo<AgentForbiddenProfitVo> page = new PageInfo<AgentForbiddenProfitVo>(list);
        return page;
    }

    @Override
    public void removeByUserIds(List<Long> userIds) {
        slaveAgentForbiddenProfitMapper.removeByUserIds(userIds);
    }
}
