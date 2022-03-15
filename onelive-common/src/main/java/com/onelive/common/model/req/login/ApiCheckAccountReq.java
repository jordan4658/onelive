package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("检查账号信息")
public class ApiCheckAccountReq {

    @ApiModelProperty(value = "用户登录账号，区号+手机号码", required = true)
    private String userAccount;


}
