package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SetWithdrawPasswordReq {

    @ApiModelProperty(value = "【必填】支付密码",required = true)
    private String  password;

    @ApiModelProperty(value = "【必填】二次确认支付密码",required = true)
    private String confirmPassword;

}
