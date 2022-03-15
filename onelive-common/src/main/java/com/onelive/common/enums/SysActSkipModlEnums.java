package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysActSkipModlEnums {
    ANCHOR_ROOM("主播房间"),
    POPULARIZE("推广返现"),
    SIGN_IN("签到赢现金"),
    JUMP_LINK("跳转链接");
    private String desc;
}
