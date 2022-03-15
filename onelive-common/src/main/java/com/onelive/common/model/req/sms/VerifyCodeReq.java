package com.onelive.common.model.req.sms;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VerifyCodeReq {

    @ApiModelProperty(value = "区号，如86，不用带+号", required = true)
    private String areaCode;

    @ApiModelProperty(value = "[必填]手机号",required = true)
    private String phone;

    @ApiModelProperty(value = "[必填]验证码",required = true)
    private String code;

    @ApiModelProperty(value = "[必填]验证码类型：1-注册、2-更新密码、3-绑定手机、4-找回密码",required = true)
    private Integer type;
}
