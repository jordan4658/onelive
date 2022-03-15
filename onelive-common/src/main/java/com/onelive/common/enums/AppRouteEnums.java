package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 可跳转的APP页面路由
 */
@Getter
@AllArgsConstructor
public enum AppRouteEnums {
    RECHARGE("充值页","recharge"),
    LIVE("直播间","live"),
    ACTIVITY("活动列表","activity");

    /**
     * 页面
     */
    private String name;
    /**
     * 路由
     */
    private String route;

}
