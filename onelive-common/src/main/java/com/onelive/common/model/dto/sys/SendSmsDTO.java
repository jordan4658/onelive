package com.onelive.common.model.dto.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SendSmsDTO
 * @Desc 发送短信实体类
 * @Date 2021/4/7 15:34
 */
@Data
public class SendSmsDTO implements Serializable {

    /**
     * 国家区号
     */
    private String areaCode;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 手机验证码
     */
    private String smsCode;

    /**
     * 短信实体id
     */
    private Long id;

}    
    