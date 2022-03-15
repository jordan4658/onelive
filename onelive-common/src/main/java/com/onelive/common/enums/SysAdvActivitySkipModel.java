package com.onelive.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 活动管理中的跳转类型
 */
@Getter
@AllArgsConstructor
public enum SysAdvActivitySkipModel {
    //跳转模块 0无 1链接 2活动 3游戏
    NONE("0","无"),
    LINK("1","链接"),
    ACTIVITY("2","2活动"),
    GAME("3","3游戏");
    /**
     * 跳转类型
     */
    private String model;
    /**
     * 类型说明
     */
    private String desc;
}
