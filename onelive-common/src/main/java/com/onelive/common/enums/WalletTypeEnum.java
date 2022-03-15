package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WalletTypeEnum {

    /**
     * 钱包类型： 1-平台钱包 2-OBG游戏钱包
     */
    /**
     * 平台钱包
     */
    WOODEN_PLATFORM(1, "ONELIVE", "平台钱包"),
    /**
     * OBG游戏钱包
     */
    GAME_OBG_ZR(2, GamePlatformEnums.OBG.getPlatformCode(), "真人游戏"),
    GAME_OBG_TY(3, GamePlatformEnums.OBG.getPlatformCode(), "体育游戏"),
    GAME_OBG_QP(4, GamePlatformEnums.OBG.getPlatformCode(), "棋牌游戏"),
    GAME_OBG_BY(5, GamePlatformEnums.OBG.getPlatformCode(), "捕鱼游戏"),
    GAME_OBG_LHJ(6, GamePlatformEnums.OBG.getPlatformCode(), "老虎机游戏");

    private Integer code;

    /**
     * 关联第三方游戏平台
     */
    private String platformCode;

    /**
     * 信息描述
     */
    private String desc;

    WalletTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    /**
     * 根据类型获取枚举
     *
     * @param code
     * @return
     */
    public static WalletTypeEnum getByType(Integer code) {
        if (code == null) {
            return null;
        }
        WalletTypeEnum[] values = values();
        for (WalletTypeEnum en : values) {
            if (en.getCode().equals(code)) {
                return en;
            }
        }
        return null;
    }
}
