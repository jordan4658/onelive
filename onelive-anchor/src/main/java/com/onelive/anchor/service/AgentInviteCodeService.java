package com.onelive.anchor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.agent.AgentInviteCodeReq;
import com.onelive.common.model.vo.agent.AgentInviteCodeVo;
import com.onelive.common.mybatis.entity.AgentInviteCode;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-01
 */
public interface AgentInviteCodeService extends IService<AgentInviteCode> {

    AgentInviteCode getOneByInviteCode(String inviteCode);

    AgentInviteCode getSysInviteCodeByUserId(Long userId);

    PageInfo<AgentInviteCodeVo> getList(AgentInviteCodeReq param);

    void updateStatusByIds(List<Long> ids, Boolean status);
}
