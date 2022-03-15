package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("注册登录信息")
public class AppRegistAndLoginReq {

    @ApiModelProperty(value = "国家编号", required = true)
    private String countryCode;

    @ApiModelProperty(value = "区号，如86", required = true)
    private String areaCode;

    @ApiModelProperty(value = "注册的手机号", required = true)
    private String mobilePhone;

    @ApiModelProperty(value = "手机验证码", required = true)
    private String smsCode;

    @ApiModelProperty(value = "图片验证码",required = true)
    private String imgCode;

    @ApiModelProperty(value = "图片标识",required = true)
    private String captchaKey;

}
