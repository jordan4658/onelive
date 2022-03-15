package com.onelive.common.model.req.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家族登录信息")
public class FamilyLoginReq {

    @ApiModelProperty(value = "登陆账号", required = true)
    private String userAccount;

    @ApiModelProperty(value = "密码", required = true)
    private String password;
    
    @ApiModelProperty(value = "平台id", required = true)
    private String merchantCode;
    
    

}
