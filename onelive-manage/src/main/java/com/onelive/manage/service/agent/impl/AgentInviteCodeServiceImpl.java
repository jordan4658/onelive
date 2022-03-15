package com.onelive.manage.service.agent.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.agent.AgentInviteCodeReq;
import com.onelive.common.model.vo.agent.AgentInviteCodeVo;
import com.onelive.common.mybatis.entity.AgentInviteCode;
import com.onelive.common.mybatis.mapper.master.agent.AgentInviteCodeMapper;
import com.onelive.common.mybatis.mapper.slave.agent.SlaveAgentInviteCodeMapper;
import com.onelive.manage.service.agent.AgentInviteCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        queryWrapper.lambda().eq(AgentInviteCode::getUserId,userId).eq(AgentInviteCode::getIsSys,true);
        return slaveAgentInviteCodeMapper.selectOne(queryWrapper);
    }


    /**
     * 查询邀请码列表
     * @param param
     * @return
     */
    @Override
    public PageInfo<AgentInviteCodeVo> getList(AgentInviteCodeReq param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<AgentInviteCodeVo> list = slaveAgentInviteCodeMapper.getList(param);
        PageInfo<AgentInviteCodeVo> page = new PageInfo<AgentInviteCodeVo>(list);
        return page;
    }

    @Override
    public void updateStatusByIds(List<Long> ids, Boolean status) {
        slaveAgentInviteCodeMapper.updateStatusByIds(ids,status);
    }
}
