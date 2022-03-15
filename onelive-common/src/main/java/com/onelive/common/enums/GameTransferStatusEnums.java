package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 游戏转账状态
 */
@Getter
@AllArgsConstructor
public enum GameTransferStatusEnums {
    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    IN_PROGRESS(2, "转账中");

    /**
     * 状态 0:成功 1:失败 2:转账中
     */
    private Integer status;
    /**
     * 描述
     */
    private String desc;
}
