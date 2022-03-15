package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName CachTypeEnums
 * @Desc 缓存类型枚举
 * @Date 2021/4/30 9:59
 */
@Getter
@AllArgsConstructor
public enum CachTypeEnums {

    /**
     * 系统参数类
     */
    SYS_PARAMS("系统参数类"),

    /**
     * 系统业务参数类
     */
    SYS_BUS_PARAMS("系统业务参数类");


    private String msg;
}    
    