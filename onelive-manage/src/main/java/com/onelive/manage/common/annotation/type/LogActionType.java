package com.onelive.manage.common.annotation.type;

/**
 * @author: liaojinlong
 * @date: 2020/6/11 19:47
 * @apiNote: 日志类型
 */

public enum LogActionType {
    /**
     * 增删改查
     */
    ADD("新增"),
    SELECT("查询"),
    UPDATE("更新"),
    DELETE("删除");
    private String value;

    LogActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
