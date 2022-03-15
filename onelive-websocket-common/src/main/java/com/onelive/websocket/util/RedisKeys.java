package com.onelive.websocket.util;

/**
 * @ClassName RedisKeys
 * @Desc REDIS 公共key值
 * @Date 2021/3/15 10:17
 */
public class RedisKeys {

    /** 登录APP的token前缀 */
    public static final String APP_TOKEN = "TOKEN:APP:";

    /** 登录账号前缀 */
    public static final String APP_LOGIN_USERINFO = "USERINFO:APP:";

    /** APP续期记录 */
    public static final String APP_ACTIVETOKEN = "ACTIVETOKEN:APP:";
    public static final String APP_ACTIVETOKEN_ACCOUNT = "ACTIVETOKEN:APP:ACCOUNT:";

    /** app的最新版本 */
    public static final String SYS_APP_LAST_VERSION = "SYS:APP:LAST_VERSION:";

    /** 系统参数缓存前缀 */
    public static final String SYS_PARAMETER_CODE = "SYS:PARAMETER_CODE:";

    /** 业务系统参数缓存前缀 */
    public static final String SYS_BUS_PARAMETER_CODE = "SYS:BUS_PARAMETER_CODE:";
    /**
     * 手机号码ip限制前缀
     */
    public static final String IP_PHONE = "LOGIN:IP_PHONE:";

    /**
     * 发送短信前缀
     */
    public final static String SEND_MSG = "LOGIN:SEND_MSG:";

    /**
     * 用户状态缓存 MEM:USER:{account}:{status}
     */
    public final static String USER_STATUS = "MEM:USER:";

    /** 根据手机号码查询的系统用户缓存前缀 */
    public final static String SYS_USER_BY_PHONE = "SYS_USER:BY_PHONE:";

    /**
     * 图片验证码前缀
     */
    public final static String CAPTCHA_KEY = "LOGIN:CAPTCHA_KEY:";

    /**
     * 国家信息
     */
    public final static String SYS_COUNTRY_INFO =  "SYS:COUNTRY:INFO:";

    /**
     * 用户等级信息
     */
    public final static String MEM_LEVEL_INFO =  "MEM:LEVEL:INFO:";

    /**
     * 用户所在地区
     */
    public final static String MEM_CURRENT_AREA="MEM:CURRENT:AREA:";

    /**
     * 登录保护，密码输错几次之后，账号锁定
     */
    public final static String LOGIN_PROTECT = "LOGIN:PROTECT:";

    /**
     * 老彩种彩票下注key值
     */
    public final static String LOCK_LOTTERY_ORDER_OLD_BET = "LOCK:LOTTERY_ORDER_OLD_BET:";

    /**
     * 新彩种彩票下注key值
     */
    public final static String LOCK_LOTTERY_ORDER_NEW_BET = "LOCK:LOTTERY_ORDER_NEW_BET:";
    
    /**
     *	直播间NUM
     */
    public final static String LIVE_STUDIO_NUM = "LIVE:STUDIO_NUM:";

    /**
     * 游客登录设备标识
     */
    public final static String LOGIN_TOURIST = "LOGIN:TOURIST:";

    /**
     * 游客登录设备标识对应的国家标识
     */
    public final static String LOGIN_TOURIST_COUNTRY_CODE = "LOGIN:TOURIST:COUNTRY_CODE:";

    /**
     * 游客登录设备标识是否已绑定手机号
     */
    public final static String LOGIN_TOURIST_IS_PHONE = "LOGIN:TOURIST:IS_PHONE:";


    ////////////////////////////////////固定key值////////////////////////////////////////////

    /**
     * 国家列表
     */
    public final static String SYS_COUNTRY_LIST = "SYS:COUNTRY:LIST";



}
