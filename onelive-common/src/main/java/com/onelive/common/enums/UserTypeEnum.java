package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName : UserTypeEnum
 * @Description : 用户账号类型枚举
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {


    general(0,"普通用户"),
    tourist(1,"游客");


    /**
     * 系统参数的代码
     */
    private Integer code;

    /**
     * 方便coder理解code的意思，无其它意义
     */
    private String remark;

}
