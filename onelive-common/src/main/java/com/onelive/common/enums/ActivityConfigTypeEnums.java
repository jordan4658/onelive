package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 活动配置类型枚举
 */
@Getter
@AllArgsConstructor
public enum ActivityConfigTypeEnums {
    POPULARIZE(1,"推广返现"),
    RED_PACKET(2,"红包活动"),
    SIGN_IN(3,"签到活动"),
    FIRST_RECHARGE(4,"首充返现");
    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;
}
