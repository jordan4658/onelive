package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 第三方游戏类型
 */
@Getter
@AllArgsConstructor
public enum GameThirdTypeEnums {
    OBG_ZR("zr", "真人游戏", GamePlatformEnums.OBG.getPlatformCode()),
    OBG_TY("ty", "体育游戏", GamePlatformEnums.OBG.getPlatformCode()),
    OBG_QP("qp", "棋牌游戏", GamePlatformEnums.OBG.getPlatformCode()),
    OBG_BY("dy", "电子游戏", GamePlatformEnums.OBG.getPlatformCode());
    /**
     * 游戏类型
     */
    private String type;
    /**
     * 信息描述
     */
    private String desc;
    /**
     * 关联第三方游戏平台
     */
    private String platformCode;
}
