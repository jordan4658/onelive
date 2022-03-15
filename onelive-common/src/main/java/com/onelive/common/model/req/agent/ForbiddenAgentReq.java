package com.onelive.common.model.req.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 禁止代理返点 查询参数
 */
@Data
@ApiModel
public class ForbiddenAgentReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID[必填]",required = true)
    private Long userId;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
