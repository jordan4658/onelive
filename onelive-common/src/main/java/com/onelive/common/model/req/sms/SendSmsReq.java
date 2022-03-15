package com.onelive.common.model.req.sms;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SendSmsReq {

    @ApiModelProperty(value = "区号，如86，不用带+号", required = true)
    private String areaCode;

    @ApiModelProperty(value = "[必填]发送手机号",required = true)
    private String phone;

    @ApiModelProperty(value = "[必填]验证码类型：1-注册、2-更新密码、3-绑定手机、4-找回密码、5-提现、6-绑定银行卡",required = true)
    private Integer sendType;

}
