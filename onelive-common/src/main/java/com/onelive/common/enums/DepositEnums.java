package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName DepositEnums
 * @Desc 入款类型枚举
 * @Date 2021/4/23 10:09
 */
@Getter
@AllArgsConstructor
public enum DepositEnums {

    /**
     * 人工入款
     */
    HANDLE("人工入款"),
    /**
     * 公司入款
     */
    COMPANY("公司入款"),
    /**
     * 线上入款
     */
    ONLINE("线上入款"),
    /**
     * 代充充值
     */
    proxy("代充充值"),
    /**
     * 小计
     */
    total("小计");

    private String msg;
}    
    