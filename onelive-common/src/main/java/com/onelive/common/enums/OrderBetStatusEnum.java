package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName OrderBetStatusEnum
 * @Desc 订单下注状态类型枚举
 */
@Getter
@AllArgsConstructor
public enum OrderBetStatusEnum {

    WIN("WIN","中奖"),
    NO_WIN("NO_WIN","未中奖"),
    HE("HE","打和"),
    WAIT("WAIT","等待开奖"),
    BACK("BACK","撤单");

    /**
     * 下注类型
     */
    private String type;

    /**
     * 描述
     */
    private String desc;

}
