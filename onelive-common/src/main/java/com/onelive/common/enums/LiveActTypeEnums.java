package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName LiveActTypeEnums
 * @Desc 直播间活动枚举
 * @Date 2021/4/8 17:06
 */
@Getter
@AllArgsConstructor
public enum LiveActTypeEnums {
    GAME(1),
    LIVE(2);
    private Integer code;
}    
    