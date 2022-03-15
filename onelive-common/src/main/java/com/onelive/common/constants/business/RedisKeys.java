package com.onelive.common.constants.business;

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

    /** 用户信息 userId:info **/
    public static final String SYS_USER_INFO = "SYS:USER:INFO:";
    /**  中奖公告文案  **/
    public static final String SYS_LIVE_NOTICE_TEXT_MAP = "SYS:LIVE:NOTICE:TEXT:MAP";
    /**
     * 用户登陆token
     */
    public static final String APP_LOGIN_USER_TOKEN = "APP:LOGIN_USER:TOKEN:";

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
     * 发送短信的间隔时间
     */
    public final static String SEND_MSG_NEXT_SEND_TIME = "LOGIN:SEND_MSG_NEXT_SEND_TIME:";

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
     * 多语言列表
     */
    public final static String SYS_LANG_LIST =  "SYS:LANG_LIST";

    /**
     * 用户等级信息
     */
    public final static String MEM_LEVEL_INFO =  "MEM:LEVEL:INFO:";

    /**
     * 用户所在地区
     */
    public final static String MEM_CURRENT_AREA="MEM:CURRENT:AREA:";

    /**
     * 用户钱包名称多语言集合
     */
    public final static String MEM_WALLET_NAME_LANG="MEM:WALLET:NAME:";


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
     *	直播间切换收费标记
     */
    public final static String STUDIO_SWITCH_CHARGE = "STUDIO_SWITCH_CHARGE:";

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

    /**
     * 彩票对应lang的名称Map lotteryId:lang:name
     */
    public static final String LOTTERY_MAP_LANG_KEY = "LOTTERY_MAP_LANG_KEY:";





    ////////////////////////////////////固定key值////////////////////////////////////////////

    /**
     * 国家列表
     */
    public final static String SYS_COUNTRY_LIST = "SYS:COUNTRY:LIST";


    //================支付相关前缀========start================
    /** 更新余额 锁前缀 */
    public final static String PAY_UPDATE_BALANCE = "PAY:UPDATE_BALANCE:";
    /** 取消提现操作 锁前缀 */
    public final static String PAY_CANCEL_WITHDRAW_BALANCE = "PAY:CANCEL_WITHDRAW_BALANCE:";
    /** 充值操作 锁前缀 */
    public final static String PAY_RECHARGE_BALANCE = "PAY:RECHARGE_BALANCE:";

    /** 更新银豆余额 锁前缀 */
    public final static String PAY_UPDATE_SILVERBEAN_BALANCE = "PAY:UPDATE_SILVERBEAN_BALANCE:";
    /** 取消银豆提现操作 锁前缀 */
    public final static String PAY_CANCEL_SILVERBEAN_WITHDRAW_BALANCE = "PAY:CANCEL_SILVERBEAN_WITHDRAW_BALANCE:";
    /** 充值银豆操作 锁前缀 */
    public final static String PAY_RECHARGE_SILVERBEAN_BALANCE = "PAY:RECHARGE_SILVERBEAN_BALANCE:";
    

    /** 弹幕 */
    public final static String SYNC_LIVE_GIFT_BARRAGE = "SYNC_LIVE_GIFT_BARRAGE:";
    
    /** 送礼 锁 */
    public final static String SYNC_LIVE_GIFT_GIVING = "SYNC_LIVE_GIFT_GIVING:";
    
    /** 关闭直播 */
    public final static String SYNC_LIVE_STUDIO_CLOSE = "SYNC_LIVE_STUDIO_CLOSE:";
    
    /** 直播间收费 */
    public final static String SYNC_LIVE_BUY_PRODUCT = "SYNC_LIVE_BUY_PRODUCT:";
    
    /** 家族长提现主播金额 */
    public final static String FAMILY_WITHDRAWANCHOR = "FAMILY_WITHDRAWANCHOR:";
    
    /** 用户关注主播 */
    public final static String SYNC_MEM_FOCUS_ANCHOR = "MEM_FOCUS_ANCHOR:";
    
    /** 用户金币换银豆 */
    public final static String SYNC_MEM_GOLD_CHANGE = "SYNC_MEM_GOLD_CHANGE:";

    // ================支付相关前缀========end================


    //=====================第三方游戏===========================
    public static final String GAME_CATEGORY = "GAME:CATEGORY:";


}
