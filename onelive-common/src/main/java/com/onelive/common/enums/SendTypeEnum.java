package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName : SendTypeEnum
 * @Description : 发送验证码类型
 */
@Getter
@AllArgsConstructor
public enum SendTypeEnum {

//    验证码类型：1-注册、2-更新密码、3-绑定手机、4-找回密码、5-提现、6-绑定银行卡
    /**
     * 注册登陆
     */
    REGISTER(1, "注册登陆"),

    /**
     * 更新密码
     */
    FINDPASSWORD(2, "更新密码"),
    /**
     * 绑定手机
     */
    BINDPHONE(3, "绑定手机"),
    /**
     * 找回密码
     */
    CHANGEPASSWORD(4, "找回密码"),
    /**
     * 提现
     */
    WITHDRAWAL(5, "提现"),
    /**
     * 绑定银行卡
     */
    BINDBANK(6, "绑定银行卡"),

    ;

    //标准值
    private Integer code;
    //标准名称
    private String value;

    
    public static String getValueByCode(Integer code) {
        for (SendTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.value;
            }
        }
        return null;
    }
}
