package com.onelive.common.model.vo.sms;

import lombok.Data;

/**
 * @ClassName : SmsResultVo
 * @Description : 第三方发短信接收类
 */
@Data
public class SmsResultVo {
    /**
     * 错误信息code值
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 发送短信的供应商
     */
    private String smsType;
}
