package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName : BindPhoneReq
 * @Description : 游客绑定手机号请求参数
 */
@Data
@ApiModel(value = "游客绑定手机号请求参数")
public class BindPhoneReq {

    @ApiModelProperty(value = "区号，如+86", required = true)
    private String areaCode;

    @ApiModelProperty(value = "注册的手机号", required = true)
    private String mobilePhone;

//    @ApiModelProperty(value = "密码", required = true)
//    private String password;

    @ApiModelProperty(value = "手机验证码", required = true)
    private String smsCode;

//    @ApiModelProperty(value = "图片验证码")
//    private String imgCode;
//
//    @ApiModelProperty(value = "图片标识")
//    private String captchaKey;
}
