package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录信息")
public class AppLoginReq {

    @ApiModelProperty(value = "区号，如+86", required = true)
    private String areaCode;

    @ApiModelProperty(value = "注册的手机号", required = true)
    private String mobilePhone;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "图片验证码")
    private String imgCode;

    @ApiModelProperty(value = "图片标识")
    private String captchaKey;

}
