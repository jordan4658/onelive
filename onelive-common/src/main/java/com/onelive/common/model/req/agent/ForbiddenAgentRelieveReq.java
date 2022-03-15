package com.onelive.common.model.req.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 解禁请求参数
 */
@Data
@ApiModel
public class ForbiddenAgentRelieveReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID列表[必填]",required = true)
    private ArrayList<Long> userIds;

}
