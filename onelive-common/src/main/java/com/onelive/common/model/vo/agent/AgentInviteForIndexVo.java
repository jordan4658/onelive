package com.onelive.common.model.vo.agent;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户端推广传输类
 */
@Data
@ApiModel
public class AgentInviteForIndexVo {

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("邀请总数(下级数量)")
    private Integer inviteCount;
    
    @ApiModelProperty("返现金(下级返点)")
    private BigDecimal inviteAward;
    
}
