package com.onelive.common.constants.other;

import lombok.AllArgsConstructor;

/**
 * @ClassName HeaderConstants
 * @Desc 请求头常量类
 * @Date 2021/3/30 16:30
 */
@AllArgsConstructor
public class HeaderConstants {

    /**
     * 请求头token的key
     */
    public static String AUTHORIZATION = "Authorization";


    /** 时间戳 */
    public static final String ONELIVETIMESTAMP = "onelive-timestamp";

    /** 随机数 */
    public static final String ONELIVERANDOM = "onelive-random";

    /** 请求url的最后字符串 */
    public static final String ONELIVEURL = "onelive-url";

    /** 签名 */
    public static final String ONELIVESIGNATURE = "onelive-signature";

    /** 来源 ios、android、pc */
    public static final String ONELIVESOURCE = "onelive-source";

    /** 代理 */
    public static final String USER_AGENT = "user-agent";

    /** 设备信息 */
    public static final String ONELIVEDEVICES = "onelive-devices";

    /** 手机设备唯一标识 */
    public static final String ONELIVEDEVICEID = "onelive-deviceId";

    /** 秘钥类型 1,2,3,4,5 */
    public static final String ONELIVEAPPLETYPE = "onelive-appleType";

    /** 语言标识信息 */
    public static final String Lang = "lang";

    /** 调试，不走加密 */
    public static final String isTest = "isTest";

}
    