package com.onelive.common.constants.business;

import lombok.AllArgsConstructor;

import java.net.InetAddress;

/**
 * @ClassName CommonConstants
 * @Desc 公共常量类
 * @Date 2021/3/30 16:25
 */
@AllArgsConstructor
public class CommonConstants {

    /** ios*/
    public static final String IOS = "ios";

    /** 安卓*/
    public static final String ANDROID = "android";

    /** int类型状态 开*/
    public static final Integer STATUS_YES = 0;
    /** int类型状态 关*/
    public static final Integer STATUS_NO = 9;
    /** 获取本机服务器ip */
    public static String LOCAL_ADDRESS = "";

    /** 主数据源名称 */
    public static final String  DATA_SOURCE_MASTER="master";
    /** 从数据源名称 */
    public static final String  DATA_SOURCE_SLAVE="slave1";

    /** API服务接口，是否开启加密 */
    public static final Boolean OPEN_SECRET=true;


    static {
        try {
            CommonConstants.LOCAL_ADDRESS = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    