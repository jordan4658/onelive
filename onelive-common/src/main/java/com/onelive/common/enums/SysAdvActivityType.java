package com.onelive.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 活动管理中的活动类型
 */
@Getter
@AllArgsConstructor
public enum SysAdvActivityType {
    //活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗
    OTHER(0,"其他"),
    GAME(1,"游戏活动"),
    LIVE(2,"直播活动"),
    INDEX_WINDOWS(3,"首页弹窗"),
    LIVE_WINDOWS(4,"直播间弹窗");
    /**
     * 活动类型
     */
    private Integer type;
    /**
     * 类型说明
     */
    private String desc;
}
