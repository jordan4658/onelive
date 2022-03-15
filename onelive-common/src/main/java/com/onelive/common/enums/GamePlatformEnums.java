package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 第三方游戏平台
 */
@Getter
@AllArgsConstructor
public enum GamePlatformEnums {

    OBG("OBG游戏平台","OBG");


    /**
     * 平台名称
     */
    private String name;

    /**
     * 平台代码 区分唯一性
     */
    private String platformCode;

}
