package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName : SysBusParamEnum
 * @Description : 系统业务参数配置枚举
 */
@Getter
@AllArgsConstructor
public enum SysBusParamEnum {
    HEADIMG("HEADIMG", "用户默认头像"),
    BANKLIST("banklist", "开户银行");

    /**
     * 系统参数的代码
     */
    private String code;

    /**
     * 方便coder理解code的意思，无其它意义
     */
    private String remark;

}

