package com.onelive.common.model.req.agent;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 禁止代理返点 查询参数
 */
@Data
@ApiModel
public class AgentForbiddenProfitReq extends PageReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号[必填]",required = true)
    private String userAccount;

    /**
     * 商户编码,默认0
     */
    @ApiModelProperty("商户编码")
    private String merchantCode;
}
