package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户端类型枚举
 */
@Getter
@AllArgsConstructor
public enum ClientType {

    USER_CLIENT(1,"用户端"),
    ANCHOR_CLIENT(2,"主播端");

    private Integer type;
    private String desc;
}
