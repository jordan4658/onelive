package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 游戏转账类型
 */
@Getter
@AllArgsConstructor
public enum GameTransferTypeEnums {
    GAME_DEPOSIT(1,"上分"),
    GAME_WITHDRAW(2,"下分");

    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;
}
