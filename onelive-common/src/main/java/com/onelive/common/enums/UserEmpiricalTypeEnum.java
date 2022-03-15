package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户经验值类型
 */
@Getter
@AllArgsConstructor
public enum UserEmpiricalTypeEnum {

    GIFTS(1,"送礼物"),
    GAME(2,"玩游戏");

    /**
     * 经验值类型
     */
    private Integer type;

    /**
     * 类型描述
     */
    private String desc;

}
