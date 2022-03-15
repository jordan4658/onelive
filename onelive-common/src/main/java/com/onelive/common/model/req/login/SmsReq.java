package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName : SmsReq
 * @Description : 短信请求类
 */
@Data
@ApiModel(value = "短信请求类")
public class SmsReq {
    @ApiModelProperty(value = "手机号码", required = true)
    private String mobilePhone;
    @ApiModelProperty(value = "手机号区号", required = true)
    private String areaCode;
    @ApiModelProperty(value = "发送短信类型 0注册登录 1绑定银行卡 2找回密码 3绑定手机号 4修改密码", required = true)
    private Integer sendType;
}
