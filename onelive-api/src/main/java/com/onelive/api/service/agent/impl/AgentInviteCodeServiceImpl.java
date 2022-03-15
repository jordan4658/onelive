package com.onelive.api.service.agent.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.agent.AgentInviteCodeService;
import com.onelive.common.mybatis.entity.AgentInviteCode;
import com.onelive.common.mybatis.mapper.master.agent.AgentInviteCodeMapper;
import com.onelive.common.mybatis.mapper.slave.agent.SlaveAgentInviteCodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-01
 */
@Service
public class AgentInviteCodeServiceImpl extends ServiceImpl<AgentInviteCodeMapper, AgentInviteCode> implements AgentInviteCodeService {

    @Resource
    private SlaveAgentInviteCodeMapper slaveAgentInviteCodeMapper;

    @Override
    public AgentInviteCode getOneByInviteCode(String inviteCode) {
        QueryWrapper<AgentInviteCode> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentInviteCode::getInviteCode,inviteCode);
        return slaveAgentInviteCodeMapper.selectOne(queryWrapper);
    }

    /**
     * 查询用户系统生成的邀请码
     * @param userId
     * @return
     */
    @Override
    public AgentInviteCode getSysInviteCodeByUserId(Long userId) {
        QueryWrapper<AgentInviteCode> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentInviteCode::getUserId,userId).eq(AgentInviteCode::getIsSys,1);
        return slaveAgentInviteCodeMapper.selectOne(queryWrapper);
    }
}
