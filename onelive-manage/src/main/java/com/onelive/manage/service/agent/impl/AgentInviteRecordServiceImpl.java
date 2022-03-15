package com.onelive.manage.service.agent.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.report.AgentReportListReq;
import com.onelive.common.model.vo.report.AgentReportListVo;
import com.onelive.common.mybatis.entity.AgentInviteRecord;
import com.onelive.common.mybatis.mapper.master.agent.AgentInviteRecordMapper;
import com.onelive.common.mybatis.mapper.slave.agent.SlaveAgentInviteRecordMapper;
import com.onelive.manage.service.agent.AgentInviteRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-16
 */
@Service
public class AgentInviteRecordServiceImpl extends ServiceImpl<AgentInviteRecordMapper, AgentInviteRecord> implements AgentInviteRecordService {

    @Resource
    private SlaveAgentInviteRecordMapper slaveAgentInviteRecordMapper;

    @Override
    public PageInfo<AgentReportListVo> getReportList(AgentReportListReq param) {
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        List<AgentReportListVo> list = slaveAgentInviteRecordMapper.getReportList(param);
        PageInfo<AgentReportListVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
