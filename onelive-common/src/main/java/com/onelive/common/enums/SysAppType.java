package com.onelive.common.enums;

/**
 * @author lorenzo
 * @Description: APP类型
 * @date 2021/4/5
 */
public enum SysAppType {
    Android(1, "android"),
    IOS(2, "ios");
    private final Integer code;
    private final String name;

    SysAppType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SysAppType getByCode(Integer code) {
        for (SysAppType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
