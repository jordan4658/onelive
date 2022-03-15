package com.onelive.api.service.agent;

import com.onelive.common.mybatis.entity.AgentInviteCode;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
