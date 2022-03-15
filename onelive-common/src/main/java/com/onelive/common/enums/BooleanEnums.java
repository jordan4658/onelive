package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName BooleanEnums
 * @Desc 布尔值枚举
 * @Date 2021/4/8 17:06
 */
@Getter
@AllArgsConstructor
public enum BooleanEnums {
    TRUE(1),
    FALSE(0);
    private Integer code;
}    
    