package com.onelive.common.model.dto.sys;

import lombok.Data;

/**
 * @ClassName SmsCodeDTO
 * @Desc 查询短信内部类
 * @Date 2021/4/7 14:24
 */
@Data
public class SmsCodeDTO {

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 短信状态码 0发送成功 8已使用 9发送失败
     */
    private Integer masStatus;

    /**
     * 短信类型 0注册登录 1绑定银行卡 2找回密码 3绑定手机号
     */
    private Integer msgType;

    /**
     * 商户code值，默认值为0
     */
    String merchantCode;

}    
    