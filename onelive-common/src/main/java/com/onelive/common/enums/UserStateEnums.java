package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName : UserStateEnums
 * @Description : 用户状态枚举
 */
@Getter
@AllArgsConstructor
public enum UserStateEnums {
    /**
     * 冻结状态
     */
    FROZENSTATUS,
    /**
     * 返点状态
     */
    COMMISSIONSTATUS,
    /**
     * 下注状态
     */
    BETSTATUS,
    /**
     * 出款状态
     */
    DISPENSINGSTATUS,
    /**
     * 直播超级管理员状态
     */
    SUPERLIVEMANAGESTATUS,
    /**
     * 当前所在国家
     */
    COUNTRY;
}
