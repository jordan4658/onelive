package com.onelive.common.constants.business;

/**
 * @ClassName LoginConstants
 * @Desc 登录注册常量类
 * @Date 2021/3/30 16:26
 */
public class LoginConstants {

    /** 账号正则表达式 首位为字母，4-11位，至少2个字母+数字组合 */
    // public static final String ACCLOGIN_REGEX = "^[a-zA-Z](?=.*[a-zA-Z])(?=.*[0-9])(.{4,11})$";
    //public static final String ACCLOGIN_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,11}$";
    public static final String ACCLOGIN_REGEX = "^[a-zA-Z](?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,11}$";
    /** APP用户端登录 */
    public static final String APP_LOGIN_INFO = "APP_LOGIN_INFO";

    /** 后台登录人信息 */
    public static final String ADMIN_LOGIN_INFO = "ADMIN_LOGIN_INFO";

    /** 手机号码正则 */
    public static final String PHONE_REGEX = "\\d{6,14}";

    /** 手机区号正则 */
    public static final String PHONE_AREA_REGEX = "\\+?\\d{1,4}";

}
