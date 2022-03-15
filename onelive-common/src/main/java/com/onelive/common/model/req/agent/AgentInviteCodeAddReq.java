package com.onelive.common.model.req.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加邀请码请求参数
 */
@Data
@ApiModel
public class AgentInviteCodeAddReq implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户ID[必填]",required = true)
    private Long userId;
    @ApiModelProperty(value = "邀请码[必填]",required = true)
    private String inviteCode;
    @ApiModelProperty(value = "状态: true正常,false停用[必填]",required = true)
    private Boolean status;

}
