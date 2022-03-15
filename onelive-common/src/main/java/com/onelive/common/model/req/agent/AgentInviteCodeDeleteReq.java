package com.onelive.common.model.req.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 添加邀请码请求参数
 */
@Data
@ApiModel
public class AgentInviteCodeDeleteReq implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "邀请码ID[必填]",required = true)
    private ArrayList<Long> ids;

}
