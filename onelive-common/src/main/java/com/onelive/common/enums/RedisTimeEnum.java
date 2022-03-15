package com.onelive.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName RedisTimeEnums
 * @Desc redis时间枚举, 单位秒
 * @Date 2021/3/15 14:15
 */

@Getter
@AllArgsConstructor
public enum RedisTimeEnum {

    /**
     * redis全局最大生命周期,默认7天
     */
    GLOBAL(7 * 24 * 60 * 60l),
    /**
     * 1小时
     */
    ONEHOUR(60 * 60l),
    /**
     * 2小时
     */
    TWOHOUR(2 * 60 * 60l),
    /**
     * 12小时
     */
    HALFDAY(12 * 60 * 60l),
    /**
     * 24小时
     */
    ONEDAY(24 * 60 * 60l),
    /**
     * redis默认过期时间，5分钟
     */
    NOSETTIME(5 * 60l);

    private final Long value;


}
    