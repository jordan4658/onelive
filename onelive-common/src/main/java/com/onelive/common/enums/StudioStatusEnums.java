package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudioStatusEnums {
    /**
     * 未开播
     */
    NOTOPEN(0),
    /**
     * 开播中
     */
    OPENING(1),
    /**
     * 网络状态不好
     */
    BADNETWORK(2);

    private Integer code;

}
