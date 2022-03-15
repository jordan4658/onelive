package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmsStatusEnum {
    success(0, "成功"),
    used(8, "已使用"),
    fail(9, "发送失败");

    //标准值
    private Integer code;
    //标准名称
    private String value;

}
