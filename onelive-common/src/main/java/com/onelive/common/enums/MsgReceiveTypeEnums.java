package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName MsgReceiveTypeEnums
 * @Desc 消息接收类型枚举
 */
@Getter
@AllArgsConstructor
public enum MsgReceiveTypeEnums {
    //用户层级
    GRUOP(1),
    //用户等级
    LEVEL(2),
    //用户账号
    ACCOUNT(3),
    //用户区域
    AREA(4);
    private Integer code;
}    
