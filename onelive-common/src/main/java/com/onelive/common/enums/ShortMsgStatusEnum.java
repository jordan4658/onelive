package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ShortMsgStatusEnum
 * @Desc 短信发送状态
 * @Date 2021/4/5 19:37
 */
@AllArgsConstructor
@Getter
public enum ShortMsgStatusEnum {
    /**
     * 注册登录
     */
    SUCCESS(0, "发送成功"),
    /**
     * 已使用
     */
    USED(8, "已使用"),
    /**
     * 发送失败
     */
    FAILED(9, "发送失败");

    //短信状态码 0发送成功 8已使用 9发送失败
    private Integer code;
    private String msg;

    public static String getDesByCode(Integer code) {
        for (ShortMsgStatusEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getMsg();
            }
        }
        return null;
    }
    

}    
    