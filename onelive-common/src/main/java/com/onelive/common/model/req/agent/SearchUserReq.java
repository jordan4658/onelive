package com.onelive.common.model.req.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class SearchUserReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号[必填]",required = true)
    private String userAccount;

}
