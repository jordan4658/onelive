package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameCodeEnums {
    /**
     * 热门
     */
    HOT("HOT", "game_hot"),
    /**
     * 彩票
     */
    LOTTERY("LOTTERY", "game_lottery"),

    /**
     * 棋牌
     */
    CHESS("CHESS", "game_chess"),
    /**
     * 体育
     */
    SPORTS("SPORTS", "game_sports"),
    /**
     * 捕鱼
     */
    FISHING("FISHING", "game_fishing"),
    /**
     * 电子
     */
    ELECTRONIC("ELECTRONIC", "game_electronic"),
    /**
     * 真人视讯
     */
    VIDEO("VIDEO", "game_video");

    private String code;
    private String msg;

    public static GameCodeEnums getByCode(String code) {
        for (GameCodeEnums type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
