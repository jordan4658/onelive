package com.onelive.common.model.req.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 修改邀请码状态
 */
@Data
@ApiModel
public class AgentInviteCodeChangeStatusReq {
    @ApiModelProperty(value = "邀请码ID列表[必填]", required = true)
    private List<Long> ids;
    /**
     * 状态:1正常,0停用
     */
    @ApiModelProperty(value = "状态:true正常,false停用[必填]",required = true)
    private Boolean status;
}
