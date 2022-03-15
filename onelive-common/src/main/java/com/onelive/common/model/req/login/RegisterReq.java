package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("注册信息")
public class RegisterReq {

    @ApiModelProperty(value = "国家编号", required = true)
    private String countryCode;

    @ApiModelProperty(value = "区号，如+86", required = true)
    private String areaCode;

    @ApiModelProperty(value = "注册的手机号", required = true)
    private String mobilePhone;

    @ApiModelProperty(value = "密码，不加密传入", required = true)
    private String password;

    @ApiModelProperty(value = "手机验证码", required = true)
    private String smsCode;

    @ApiModelProperty(value = "图片验证码")
    private String imgCode;

    @ApiModelProperty(value = "图片标识")
    private String captchaKey;

    @ApiModelProperty(value = "邀请码,待定，获取方式后期与前端协调")
    private String inviteCode;


}
