package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName SmsThirdEnum
 * @Desc 第三方短信供应商
 * @Date 2021/4/3 17:48
 */
@Getter
@AllArgsConstructor
public enum SmsThirdEnum {
    YunTongXin("YunTongXin", "云通信", "飞狐科技");
    private String code;
    private String msg;
    private String sign;
}    
    