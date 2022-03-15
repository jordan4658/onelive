package com.onelive.common.constants.business;

/**
 * @ClassName LotteryRedisKeys
 * @Desc REDIS 彩票key值
 * @Date 2021/3/15 10:17
 */
public class LotteryRedisKeys {

    /**
     * 系统信息值，缓存后缀
     */
    public static final String SYSTEM_INFO_VALUE_SUFFIX = "_INFO";

    /**
     * 用户对象key: key后拼接id
     */
    public static final String APP_MEMBER = "LIVE_APP_MEMBER_";

    /**
     * ACCNO 与MEMID的映射关系
     */
    public static final String ACCNO_MAP_MEMID = "ACCNO_MAP_MEMID";

    /**
     * UNIQUE_ID 与MEMID的映射关系
     */
    public static final String UNIQUE_ID_MAP_MEMID = "UNIQUE_ID_MAP_MEMID_";

    /**
     * 用户名称key: key后拼接id
     */
    public static final String APP_MEMBER_IDANDNAME = "LIVE_APP_MEMBER_IDANDNAME_";

    /**
     * 后台用户名称key: key后拼接id
     */
    public static final String REFERRE_MEMBER_IDANDNAME = "LIVE_REFERRE_MEMBER_IDANDNAME_";


    /**
     * 用户登录表对象key: key后拼接登录名
     */
    public static final String MEM_LOGIN_ACCLOGIN = "LIVE_LOGIN_ACCLOGIN_";
    /**
     *
     */
    public static final String BD_USER_INFO = "BD_USER_INFO";
    /**
     * 系统用户缓存对象key: key后拼接sfunid
     */
    public static final String SYSTEM_SYSFUNCTIONORG_SFUNID = "LIVE_SYSFUNCTIONORG_SFUNID_";
    /**
     * 系统用户缓存列表对象key: key后拼接parsfunid
     */
    public static final String SYSTEM_SYSFUNCTIONORG_PARSFUNID = "LIVE_SYSFUNCTIONORG_PARSFUNID_";

    /**
     * 家族长提现管理缓存列表对象key: key后拼接parsfunid
     */
    public static final String FAMILY_CHIEF_WITHDRAWAL_MANAGEMENT = "LIVE_FAMILY_CHIEF_WITHDRAWAL_MANAGEMENT_";

    /**
     * 系统用户key: key后拼接phoneno
     */
    public static final String SYSTEM_USER_PHONENO = "LIVE_SYSTEM_USER_PHONENO_";

    /**
     * 心水对于游客缓存帖子详细数据 KEY
     */
    public static final String CUSTOMER_RECOMEND_CONTENT = "LIVE_CUSTOMER_RECOMMEND_CONTENT_";

    public static final String TOURISTS_RECOMEND_CONTENT = "LIVE_TOURISTS_RECOMMEND_CONTENT_";

    /**
     * 所有彩种集合key
     */
    //---------------------------带国际化的内容 start-----------------------------------------------------------
    public static final String LOTTERY_CATEGORY_LIST_LANG_KEY = "ONE_LIVE_LOTTERYCATEGORY_LIST_LANG_KEY:";
    public static final String LOTTERY_CATEGORY_MAP_LANG_KEY = "ONE_LIVE_LOTTERYCATEGORY_MAP_LANG_KEY:";
    public static final String LOTTERY_ALL_INFO_LANG = "ONE_LIVE_LOTTERY_ALL_INFO_LANG:";
    public static final String LOTTERY_LIST_LANG_KEY = "ONE_LIVE_LOTTERYLIST_LANG_KEY:";
    public static final String LOTTERY_MAP_LANG_KEY = "ONE_LIVE_LOTTERYMAP_LANG_KEY:";
    public static final String LOTTERY_PLAY_LIST_LANG_KEY = "ONE_LIVE_LOTTERYPLAY_LIST_LANG_KEY:";
    public static final String LOTTERY_PLAY_SETTING_ALL_DATA_LANG = "ONE_LIVE_LOTTERYPLAY_SETTING_ALL_DATA_LANG:";
    public static final String LOTTERY_PLAY_ODDS_ALL_DATA_LANG = "ONE_LIVE_LOTTERYPLAY_ODDS_ALL_DATA_LANG:";
    //---------------------------带国际化的内容 end-------------------------------------------------------------

    public static final String LOTTERY_CATEGORY_LIST_KEY = "ONE_LIVE_LOTTERY_CATEGORY_LIST_KEY";
    public static final String LOTTERY_CATEGORY_MAP_KEY = "ONE_LIVE_LOTTERY_CATEGORY_MAP_KEY";
    public static final String LOTTERY_KEY = "ONE_LIVE_LOTTERY_KEY_";
    public static final String LOTTERY_ALL_INFO = "ONE_LIVE_LOTTERY_ALL_INFO";
    public static final String LOTTERY_ALL_INNER_LIST = "ONE_LIVE_LOTTERY_ALL_INNER_LIST";
    public static final String LOTTERY_CATEGORY_ID = "ONE_LIVE_LOTTERY_CATEGORY_ID";

    public static final String LOTTERY_ALL_INFO_LIST = "LOTTERY_ALL_INFO_LIST";
    public static final String LOTTERY_MAP_INFO = "LOTTERY_MAP_INFO";

    /**
     * 所有彩票集合key
     */
    public static final String LOTTERY_LIST_KEY = "ONE_LIVE_LOTTERY_LIST_KEY";
    /**
     * 所有彩种MAP集合key
     */
    public static final String LOTTERY_MAP_KEY = "ONE_LIVE_LOTTERY_MAP_KEY";

    /**
     * 所有彩票集合key：包含已删除彩种
     */
    public static final String LOTTERY_ALL_LIST_KEY = "ONE_LIVE_LOTTERY_ALL_LIST_KEY";
    /**
     * 所有彩种MAP集合key：包含已删除彩种
     */
    public static final String LOTTERY_ALL_MAP_KEY = "ONE_LIVE_LOTTERY_ALL_MAP_KEY";

    /**
     * 所有彩种玩法集合key
     */
    public static final String LOTTERY_PLAY_LIST_KEY = "ONE_LIVE_LOTTERY_PLAY_LIST_KEY";

    /**
     * 所有彩种玩法MAP集合key
     */
    public static final String LOTTERY_PLAY_MAP_KEY = "ONE_LIVE_LOTTERY_PLAY_MAP_KEY";

    /**
     * 投注限制对象key: key后拼接id
     */
    public static final String BONUS_KEY = "LIVE_BONUS_KEY_";

    /**
     * 赔率配置列表
     */
    public static final String ODDS_LIST_SETTING_KEY = "LIVE_ODDS_LIST_SETTING_";

    /**
     * 赔率配置列表
     */
    public static final String ODDS_LIST_LONG_Dragon_SETTING_KEY = "LIVE_ODDS_LIST_LONG_Dragon_SETTING_KEY_";

    /**
     * 六合彩赔率配置列表
     */
    public static final String LHC_ODDS_LIST_LONG_DRAGON_SETTING_KEY = "LIVE_LHC_ODDS_LIST_LONG_Dragon_SETTING_KEY_";

    /**
     * 数据值等级列表
     */
    public static final String DATA_VALUE_LEVEL = "LIVE_DATA_VALUE_LEVEL_";

    /**
     * 心水更新点赞、阅读数列表
     */
    public static final String XS_ADMIRE_COMTENT_VALUE = "LIVE_XS_ADMIRE_COMTENT_VALUE_CACLE";

    /**
     * 后台用户
     **/
    public static final String REFERRE_MEMBER_ALL = "LIVE_REFERRE_MEMBER_ALL";

    /**
     * ==================================赛果缓存============================================*
     */

    /**
     * 重庆时时彩赛果
     */
    public static final String CQSSC_RESULT_VALUE = "LIVE_CQSSC_RESULT_VALUE_1101";

    public static final String CQSSC_NEXT_VALUE = "LIVE_CQSSC_NEXT_VALUE_1101";

    public static final String CQSSC_ALGORITHM_VALUE = "LIVE_CQSSC_ALGORITHM_VALUE_1101";// 计算单双，大小，五行数据

    /**
     * 新疆时时彩赛果
     */
    public static final String XJSSC_RESULT_VALUE = "LIVE_XJSSC_RESULT_VALUE_1102";

    public static final String XJSSC_NEXT_VALUE = "LIVE_XJSSC_NEXT_VALUE_1102";

    public static final String XJSSC_ALGORITHM_VALUE = "LIVE_XJSSC_ALGORITHM_VALUE_1102";// 计算单双，大小，五行数据

    /**
     * 天津时时彩赛果
     */
    public static final String TJSSC_RESULT_VALUE = "LIVE_TJSSC_RESULT_VALUE_1103";

    public static final String TJSSC_NEXT_VALUE = "LIVE_TJSSC_NEXT_VALUE_1103";

    public static final String TJSSC_ALGORITHM_VALUE = "LIVE_TJSSC_ALGORITHM_VALUE_1103";// 计算单双，大小，五行数据

    /**
     * 10分时时彩赛果
     */
    public static final String TENSSC_RESULT_VALUE = "LIVE_TENSSC_RESULT_VALUE_1104";

    public static final String TENSSC_NEXT_VALUE = "LIVE_TENSSC_NEXT_VALUE_1104";

    public static final String TENSSC_ALGORITHM_VALUE = "LIVE_TENSSC_ALGORITHM_VALUE_1104";// 计算单双，大小，五行数据

    /**
     * 5分时时彩赛果
     */
    public static final String FIVESSC_RESULT_VALUE = "LIVE_FIVESSC_RESULT_VALUE_1105";

    public static final String FIVESSC_NEXT_VALUE = "LIVE_FIVESSC_NEXT_VALUE_1105";

    public static final String FIVESSC_ALGORITHM_VALUE = "LIVE_FIVESSC_ALGORITHM_VALUE_1105";// 计算单双，大小，五行数据

    /**
     * 德州时时彩赛果
     */
    public static final String JSSSC_RESULT_VALUE = "LIVE_JSSSC_RESULT_VALUE_1106";

    public static final String JSSSC_NEXT_VALUE = "LIVE_JSSSC_NEXT_VALUE_1106";

    public static final String JSSSC_ALGORITHM_VALUE = "LIVE_JSSSC_ALGORITHM_VALUE_1106";// 计算单双，大小，五行数据

    /**
     * 六合彩赛果
     */
    public static final String LHC_RESULT_VALUE = "LIVE_LHC_RESULT_VALUE_1201";
    /**
     * 计算单双，大小，合单合双  尾大小 家禽
     **/
    public static final String LHC_ALGORITHM_VALUE = "LIVE_ONELHC_ALGORITHM_VALUE_1201";// 计算单双，大小，合单合双  尾大小 家禽

    /**
     * 1分六合彩赛果
     */
    public static final String ONELHC_RESULT_VALUE = "LIVE_ONELHC_RESULT_VALUE_1202";

    public static final String ONELHC_NEXT_VALUE = "LIVE_ONELHC_NEXT_VALUE_1202";

    public static final String ONELHC_OPEN_VALUE = "LIVE_ONELHC_OPEN_VALUE_1202";

    public static final String ONELHC_ALGORITHM_VALUE = "LIVE_ONELHC_ALGORITHM_VALUE_1202";// 计算单双，大小，五行数据

    /**
     * 5分六合彩赛果
     */
    public static final String FIVELHC_RESULT_VALUE = "LIVE_FIVELHC_RESULT_VALUE_1203";

    public static final String FIVELHC_NEXT_VALUE = "LIVE_FIVELHC_NEXT_VALUE_1203";

    public static final String FIVELHC_OPEN_VALUE = "LIVE_FIVELHC_OPEN_VALUE_1203";

    public static final String FIVELHC_ALGORITHM_VALUE = "LIVE_FIVELHC_ALGORITHM_VALUE_1203";// 计算单双，大小，五行数据

    /**
     * 时时六合彩赛果
     */
    public static final String AMLHC_RESULT_VALUE = "LIVE_AMLHC_RESULT_VALUE_1204";

    public static final String AMLHC_NEXT_VALUE = "LIVE_AMLHC_NEXT_VALUE_1204";

    public static final String AMLHC_OPEN_VALUE = "LIVE_AMLHC_OPEN_VALUE_1204";

    public static final String AMLHC_ALGORITHM_VALUE = "LIVE_AMLHC_ALGORITHM_VALUE_1204";// 计算单双，大小，五行数据

    /**
     * 1分六合彩赛果
     */
    public static final String XJPLHC_RESULT_VALUE = "LIVE_XJPLHC_RESULT_VALUE_1205";

    public static final String XJPLHC_NEXT_VALUE = "LIVE_XJPLHC_NEXT_VALUE_1205";

    public static final String XJPLHC_OPEN_VALUE = "LIVE_XJPLHC_OPEN_VALUE_1205";

    public static final String XJPLHC_ALGORITHM_VALUE = "LIVE_XJPLHC_ALGORITHM_VALUE_1205";// 计算单双，大小，五行数据

    /**
     * 北京PK10赛果
     */
    public static final String BJPKS_RESULT_VALUE = "LIVE_BJPKS_RESULT_VALUE_1301";

    public static final String BJPKS_NEXT_VALUE = "LIVE_BJPKS_NEXT_VALUE_1301";

    public static final String BJPKS_OPEN_VALUE = "LIVE_BJPKS_OPEN_VALUE_1301";

    public static final String BJPKS_ALGORITHM_VALUE = "LIVE_BJPKS_ALGORITHM_VALUE_1301";// 计算单双，大小，五行数据

    /**
     * 10分PK10赛果
     */
    public static final String TENPKS_RESULT_VALUE = "LIVE_TENPKS_RESULT_VALUE_1302";

    public static final String TENPKS_NEXT_VALUE = "LIVE_TENPKS_NEXT_VALUE_1302";

    public static final String TENPKS_OPEN_VALUE = "LIVE_TENPKS_OPEN_VALUE_1302";

    public static final String TENPKS_ALGORITHM_VALUE = "LIVE_TENPKS_ALGORITHM_VALUE_1302";// 计算单双，大小，五行数据

    /**
     * 5分PK10赛果
     */
    public static final String FIVEPKS_RESULT_VALUE = "LIVE_FIVEPKS_RESULT_VALUE_1303";

    public static final String FIVEPKS_NEXT_VALUE = "LIVE_FIVEPKS_NEXT_VALUE_1303";

    public static final String FIVEPKS_OPEN_VALUE = "LIVE_FIVEPKS_OPEN_VALUE_1303";

    public static final String FIVEPKS_ALGORITHM_VALUE = "LIVE_FIVEPKS_ALGORITHM_VALUE_1303";// 计算单双，大小，五行数据

    /**
     * 德州PK10赛果
     */
    public static final String JSPKS_RESULT_VALUE = "LIVE_JSPKS_RESULT_VALUE_1304";

    public static final String JSPKS_NEXT_VALUE = "LIVE_JSPKS_NEXT_VALUE_1304";

    public static final String JSPKS_OPEN_VALUE = "LIVE_JSPKS_OPEN_VALUE_1304";

    public static final String JSPKS_ALGORITHM_VALUE = "LIVE_JSPKS_ALGORITHM_VALUE_1304";// 计算单双，大小，五行数据

    /**
     * 幸运飞艇赛果
     */
    public static final String XYFEIT_RESULT_VALUE = "LIVE_XYFEIT_RESULT_VALUE_1401";

    public static final String XYFEIT_NEXT_VALUE = "LIVE_XYFEIT_NEXT_VALUE_1401";

    public static final String XYFEIT_OPEN_VALUE = "LIVE_XYFEIT_OPEN_VALUE_1401";

    public static final String XYFEIT_ALGORITHM_VALUE = "LIVE_XYFEIT_ALGORITHM_VALUE_1401";// 计算单双，大小，五行数据

    /**
     * 德州幸运飞艇赛果
     */
    public static final String DZXYFEIT_RESULT_VALUE = "LIVE_XYFEIT_RESULT_VALUE_1402";

    public static final String DZXYFEIT_NEXT_VALUE = "LIVE_XYFEIT_NEXT_VALUE_1402";

    public static final String DZXYFEIT_OPEN_VALUE = "LIVE_XYFEIT_OPEN_VALUE_1402";

    public static final String DZXYFEIT_ALGORITHM_VALUE = "LIVE_XYFEIT_ALGORITHM_VALUE_1402";// 计算单双，大小，五行数据

    /**
     * PC蛋蛋赛果
     */
    public static final String PCDAND_RESULT_VALUE = "LIVE_PCDAND_RESULT_VALUE_1501";

    public static final String PCDAND_NEXT_VALUE = "LIVE_PCDAND_NEXT_VALUE_1501";

    public static final String PCDAND_OPEN_VALUE = "LIVE_PCDAND_OPEN_VALUE_1501";

    public static final String PCDAND_ALGORITHM_VALUE = "LIVE_PCDAND_ALGORITHM_VALUE_1501";// 计算单双，大小，五行数据
    /**
     * 德州PC蛋蛋赛果
     */
    public static final String DZPCDAND_RESULT_VALUE = "LIVE_DZPCDAND_RESULT_VALUE_1502";

    public static final String DZPCDAND_NEXT_VALUE = "LIVE_DZPCDAND_NEXT_VALUE_1502";

    public static final String DZPCDAND_OPEN_VALUE = "LIVE_DZPCDAND_OPEN_VALUE_1502";

    public static final String DZPCDAND_ALGORITHM_VALUE = "LIVE_DZPCDAND_ALGORITHM_VALUE_1502";// 计算单双，大小，五行数据

    /**
     * 比特币分分彩赛果
     */
    public static final String TXFFC_RESULT_VALUE = "LIVE_TXFFC_RESULT_VALUE_1601";

    public static final String TXFFC_NEXT_VALUE = "LIVE_TXFFC_NEXT_VALUE_1601";

    public static final String TXFFC_OPEN_VALUE = "LIVE_TXFFC_OPEN_VALUE_1601";

    public static final String TXFFC_ALGORITHM_VALUE = "LIVE_TXFFC_ALGORITHM_VALUE_1601";// 计算单双，大小，五行数据

    /**
     * 大乐透赛果
     */
    public static final String DLT_RESULT_VALUE = "LIVE_DLT_RESULT_VALUE_1701";

    public static final String DLT_NEXT_VALUE = "LIVE_DLT_NEXT_VALUE_1701";

    /**
     * 排列3/5赛果
     */
    public static final String TCPLW_RESULT_VALUE = "LIVE_TCPLW_RESULT_VALUE_1702";

    /**
     * 7星彩赛果
     */
    public static final String TC7XC_RESULT_VALUE = "LIVE_TC7XC_RESULT_VALUE_1703";

    /**
     * 双色球赛果
     */
    public static final String FCSSQ_RESULT_VALUE = "LIVE_FCSSQ_RESULT_VALUE_1801";

    /**
     * 福彩3D赛果
     */
    public static final String FC3D_RESULT_VALUE = "LIVE_FC3D_RESULT_VALUE_1802";

    /**
     * 七乐彩赛果
     */
    public static final String FC7LC_RESULT_VALUE = "LIVE_FC7LC_RESULT_VALUE_1803";

    /**
     * 快乐牛牛赛果
     */
    public static final String KLNIU_RESULT_VALUE = "LIVE_KLNIU_RESULT_VALUE_1901";

    /**
     * 澳洲牛牛赛果
     */
    public static final String AZNIU_RESULT_VALUE = "LIVE_AZNIU_RESULT_VALUE_1902";

    /**
     * 德州牛牛赛果
     */
    public static final String JSNIU_RESULT_VALUE = "LIVE_JSNIU_RESULT_VALUE_1903";

    /**
     * 德州PK10番摊赛果
     */
    public static final String JSPKFT_RESULT_VALUE = "LIVE_JSPKFT_RESULT_VALUE_2001";

    public static final String JSPKFT_NEXT_VALUE = "LIVE_JSPKFT_NEXT_VALUE_2001";

    public static final String JSPKFT_OPEN_VALUE = "LIVE_JSPKFT_OPEN_VALUE_2001";

    /**
     * 幸运飞艇番摊赛果
     */
    public static final String XYFTFT_RESULT_VALUE = "LIVE_XYFTFT_RESULT_VALUE_2002";

    public static final String XYFTFT_NEXT_VALUE = "LIVE_XYFTFT_NEXT_VALUE_2002";

    public static final String XYFTFT_OPEN_VALUE = "LIVE_XYFTFT_OPEN_VALUE_2002";

    /**
     * 德州时时彩番摊赛果
     */
    public static final String JSSSCFT_RESULT_VALUE = "LIVE_JSSSCFT_RESULT_VALUE_2003";

    public static final String JSSSCFT_NEXT_VALUE = "LIVE_JSSSCFT_NEXT_VALUE_2003";

    /**
     * 澳洲ACT赛果
     */
    public static final String AUSACT_RESULT_VALUE = "LIVE_AUSACT_RESULT_VALUE_2201";

    public static final String AUSACT_NEXT_VALUE = "LIVE_AUSACT_NEXT_VALUE_2201";

    public static final String AUSACT_OPEN_VALUE = "LIVE_AUSACT_OPEN_VALUE_2201";

    public static final String AUSACT_ALGORITHM_VALUE = "LIVE_AUSACT_ALGORITHM_VALUE_2201";// 计算单双，大小，五行数据

    /**
     * 澳洲时时彩赛果
     */
    public static final String AUZSSC_RESULT_VALUE = "LIVE_AUZSSC_RESULT_VALUE_2202";

    public static final String AUZSSC_NEXT_VALUE = "LIVE_AUZSSC_NEXT_VALUE_2202";

    public static final String AUZSSC_OPEN_VALUE = "LIVE_AUZSSC_OPEN_VALUE_2202";

    /**
     * 澳洲澳洲F1赛果
     */
    public static final String AUSPKS_RESULT_VALUE = "LIVE_AUSPKS_RESULT_VALUE_2203";

    public static final String AUSPKS_NEXT_VALUE = "LIVE_AUSPKS_NEXT_VALUE_2203";

    public static final String AUSPKS_OPEN_VALUE = "LIVE_AUSPKS_OPEN_VALUE_2203";

    public static final String AUSPKS_ALGORITHM_VALUE = "LIVE_AUSPKS_ALGORITHM_VALUE_2203";// 计算单双，大小，五行数据

    /**
     * 澳洲快三彩赛果
     */
    public static final String AZKS_RESULT_VALUE = "LIVE_AZKS_RESULT_VALUE_2301";

    public static final String AZKS_NEXT_VALUE = "LIVE_AZKS_NEXT_VALUE_2301";

    public static final String AZKS_OPEN_VALUE = "LIVE_AZKS_OPEN_VALUE_2301";

    public static final String AZKS_ALGORITHM_VALUE = "LIVE_AUSPKS_ALGORITHM_VALUE_2301";// 计算单双，大小，五行数据

    /**
     * 德州快三彩赛果
     */
    public static final String DZKS_RESULT_VALUE = "LIVE_DZKS_RESULT_VALUE_2302";

    public static final String DZKS_NEXT_VALUE = "LIVE_DZKS_NEXT_VALUE_2302";

    public static final String DZKS_OPEN_VALUE = "LIVE_DZKS_OPEN_VALUE_2302";

    public static final String DZKS_ALGORITHM_VALUE = "LIVE_AUSPKS_ALGORITHM_VALUE_2302";// 计算单双，大小，五行数据
    /**
     * ==================================WEB缓存============================================*
     */
    /**
     * 六合彩心水推荐列表集合
     */
    public static final String APP_GETLHCXSRECOMMENDS_RESULT_VALUE = "LIVE_appGetlhcxsrecommends_result_value_";

    public static final String WEB_GETLHCXSRECOMMENDS_RESULT_VALUE = "LIVE_webGetlhcxsrecommends_result_value_";


    /**
     * 私彩杀号配置
     */
    public static final String SICAIONELHCRATE = "_SICAIONELHCRATE";
    public static final String SICAIONESSCRATE = "_SICAIONESSCRATE";
    public static final String SICAIONEPKSRATE = "_SICAIONEPKSRATE";
    public static final String SICAIFIVELHCRATE = "_SICAIFIVELHCRATE";
    public static final String SICAIFIVESSCRATE = "_SICAIFIVESSCRATE";
    public static final String SICAIFIVEPKSRATE = "_SICAIFIVEPKSRATE";
    public static final String SICAITENLHCRATE = "_SICAITENLHCRATE";
    public static final String SICAITENSSCRATE = "_SICAITENSSCRATE";
    public static final String SICAITENPKSRATE = "_SICAITENPKSRATE";
    public static final String SICAITXFFCRATE = "_SICAITXFFCRATE";
    public static final String SICAIAZKSRATE = "_SICAIAZKSRATE";
    public static final String SICAIDZKSRATE = "_SICAIDZKSRATE";
    public static final String SICAIDZPCEGGRATE = "_SICAIDZPCEGGRATE";
    public static final String SICAIDZXYFTRATE = "_SICAIDZXYFTRATE";
    public static final String SICAIXJPLHCRATE = "_SICAIXJPLHCRATE";
    public static final String KILLORDERTIME = "_KILLORDERTIME";

    /**************** 聊天室内容 *******************/
    public static final String CHAT_ROOM_VALUE = "LIVE_CHAT_ROOM_VALUE_";
    public static final String CHAT_ROOM_MQTT_VALUE = "LIVE_CHAT_ROOM_MQTT_VALUE_";
    public static final String CHAT_LOTTERY_VALUE = "LIVE_CHAT_LOTTERY_VALUE";

    /**
     * 聊天室TOPIC
     */
    public final static String APP_CHAT_KEY = "LIVE_WS_APP_CHAT_KEY";

    /********************** 支付设置 ************************/
    public static final String PAY_SET_VALUE = "LIVE_PAY_SET_VALUE";

    /********************** 支付额度设置 ************************/
    public static final String PAY_QUOTA_VALUE = "LIVE_PAY_QUOTA_VALUE";
    /**
     * 人工充值默认额度
     */
    public static final String PAY_QUOTA_MANUAL_DEFAULT_VALUE = "LIVE_PAY_QUOTA_MANUAL_DEFAULT_VALUE";
    public static final String PAY_QUOTA_RECHARGE_ACCOUNT_PREFIX = "LIVE_PAY_QUOTA_RECHARGE_ACCOUNT_";

    /********************** 支付方式设置 ************************/
    public static final String PAY_WAY_VALUE = "LIVE_PAY_WAY_VALUE";
    public static final String PAY_WAY_LIST_VALUE = "LIVE_PAY_WAY_LIST_VALUE";
    public static final String PAY_WAY_MAP_VALUE = "LIVE_PAY_WAY_MAP_VALUE";

    /**
     * 下注结算
     */
    public static final String ORDER_LIST = "LIVE_ORDER_";
    public static final long ORDER_TIME = 3600 * 24;
    public static final String BALANCECHANGE = "LIVE_BALANCECHANGE_";

    /**
     * 随机码 - 推广码
     */
    public static final String SYSTEM_RANDOM_CODE_PROMOTION_REDISSON_LOCK_WRITE = "LIVE_PROMOTION_REDISSON_LOCK_WRITE";
    public static final String SYSTEM_RANDOM_CODE_PROMOTION_REDISSON_LOCK_READ = "LIVE__PROMOTION_REDISSON_LOCK_READ";
    public static final String SYSTEM_RANDOM_CODE_PROMOTION_REDISSON_LOCK_UPDATE = "LIVE_PROMOTION_REDISSON_LOCK_UPDATE";
    public static final String SYSTEM_RANDOM_CODE_CACHES_PREFIX = "LIVE_SYSTEM_RANDOM_CODE_CACHES_";
    public static final String SYSTEM_RANDOM_CODE_USED_LIST = "LIVE_SYSTEM_RANDOM_CODE_USED_LIST";

    /**
     * 缓存点赞
     */
    public static final String MOBILE_ADMIRE_SUMNUMBER_ID = "LIVE_mobile_admire_sumNumber_id_";
    /**
     * 缓存取消点赞
     */
    public static final String MOBILE_ADMIRE_CANCELNUMBER_ID = "LIVE_mobile_admire_cancel_id_";
    /**
     * 用户提现时缓存标记：USER_WITHDRAW_${user_id}
     */
    public static final String USER_WITHDRAW_PREFIX = "LIVE_USER_WITHDRAW_";

    /**
     * 六合彩图库爬取redis lock key
     */
    public static final String CRAWLER_LHC_TK_KJ_LOCK = "LIVE_CRAWLER_LHC_TK_KJ_LOCK";
    /**
     * 用户离线时间统计
     */
    public static final String STAT_USER_OFFLINE_TIME_MAP = "LIVE_STAT_USER_OFFLINE_TIME_MAP";

    /**
     * 心水对于游客缓存结果数据 KEY
     */
    public static final String XSTJ_RECOMEND_DATE = "LIVE_XSTJ_RECOMMEND_";

    /**
     * 心水对于游客缓存帖子详细数据 KEY
     */
    public static final String XSTJ_RECOMEND_CONTENT = "LIVE_XSTJ_RECOMMEND_CONTENT_";

    /**
     * 心水推荐缓存key
     */
    public static final String XS_CACHE_KEY_HASH = "LIVE_XS_CACHE_KEY_HASH";

    /**
     * 用户，收藏彩票
     */
    public static final String LOTTERY_FAVORITE_USER_PREFIX = "ONE_LIVE_LOTTERY_FAVORITE_USER_";
    /**
     * 用户默认收藏彩票
     */
    public static final String LOTTERY_FAVORITE_DEFAULT = "ONE_LIVE_LOTTERY_FAVORITE_DEFAULT";
    /**
     * 用户收藏的最终数据展示
     */
    public static final String LOTTERY_FAVORITE_USER_DATA_PREFIX = "ONE_LIVE_LOTTERY_FAVORITE_DATA_USER_";
    /**
     * 用户默认收藏的最终数据展示
     */
    public static final String LOTTERY_FAVORITE_USER_DATA_DEFAULT = "ONE_LIVE_LOTTERY_FAVORITE_USER_DATA_DEFAULT";

    /**
     * 结算id
     */
    public static final String ORDER_CLEAR = "LIVE_ORDER_CLEAR_";

    /**
     * 直播间跟投
     */
    public static final String BROADCAST = "Live_broadcast_";

    /**
     * 中奖推送
     */
    public static final String WIN_PUSH = "LIVE_WIN_PUSH_";

    /**
     * 所有玩法设置信息
     */
    public static final String LOTTERY_PLAY_SETTING_ALL_DATA = "ONE_LIVE_LOTTERY_PLAY_SETTING_ALL_DATA";
    public static final String LOTTERY_PLAY_SETTING_MAP_TYPE = "ONE_LIVE_LOTTERYPLAY_SETTING_MAP_TYPE_";
    /**
     * 所有玩法赔率信息
     */
    public static final String LOTTERY_PLAY_ODDS_ALL_DATA = "ONE_LIVE_LOTTERY_PLAY_ODDS_ALL_DATA";
    /**
     * 所有玩法投注额限制
     */
    public static final String RESTRICT_LIST_KEY = "LIVE_RESTRICT_LIST_KEY";
    /**
     * VIP 等级列表
     */
    public static final String VIP_GRADE_LIST_KEY = "LIVE_VIP_GRADE_LIST_KEY";

    /********************** 限制发帖子的KEY ************************/
    public static final String RECOMMEND_SEND_LIMIT_TIME = "LIVE_LIMITSENDRECOMMEND";

    /**
     * 充值账户列表
     */
    public static final String RECHARGE_ACCOUNT_DTO_LIST_KEY = "LIVE_RECHARGE_ACCOUNT_DTO_LIST_KEY";
    public static final String RECHARGE_ACCOUNT_LIST_KEY = "LIVE_RECHARGE_ACCOUNT_LIST_KEY";
    public static final String RECHARGE_ACCOUNT_RECEIVE_UPDATE_KEY = "LIVE_RECHARGE_ACCOUNT_RECEIVE_UPDATE_";

    public static final String APP_MEMBER_VIP_UPGRADE_LOCK_PREFIX = "LIVE_APP_MEMBER_VIP_UPGRADE_LOCK_";
    public static final String APP_MEMBER_VIP_BACKWATER_LOCK_PREFIX = "LIVE_APP_MEMBER_VIP_BACKWATER_LOCK_";
    public static final String APP_MEMBER_VIP_UPGRADE_LOCK = "LIVE_APP_MEMBER_VIP_UPGRADE_LOCK";
    public static final String APP_MEMBER_VIP_BACKWATER_LOCK = "LIVE_APP_MEMBER_VIP_BACKWATER_LOCK";

    /**
     * 用户每日发送帖子数量统计
     */
    public static final String USER_SEND_XSRECOMMEND_NUM = "LIVE_USER_SEND_XSRECOMMEND_NUM";
    /**
     * 用户每日帖子评论数量统计
     */
    public static final String USER_SEND_RECOMMENDCOENT_NUM = "LIVE_USER_SEND_RECOMMENDCOENT_NUM";
    /**
     * 用户点赞帖子数量统计
     */
    public static final String USER_ADMIRE_RECOMMEND_NUM = "LIVE_USER_ADMIRE_RECOMMEND_NUM";
    /**
     * 用户图片评论数量统计
     */
    public static final String USER_SEND_PHOTODCOMENT_NUM = "LIVE_USER_SEND_PHOTODCOMENT_NUM";

    /**
     * 用户发帖子数量统计
     */
    public static final String SEND_XSRECOMMEND_NUM = "LIVE_SEND_XSRECOMMEND_NUM";
    /**
     * 用户修改帖子数量统计
     */
    public static final String USER_UPDATE_XSRECOMMEND_NUM = "LIVE_USER_UPDATE_XSRECOMMEND_NUM";
    /**
     * 用户点赞帖子数量统计
     */
    public static final String USER_ADMIRE_XSRECOMMEND_NUM = "LIVE_USER_ADMIRE_XSRECOMMEND_NUM";
    /**
     * 用户添加图片评论点赞帖子数量统计
     */
    public static final String USER_ADD_PICUTURECOMMEND_NUM = "LIVE_USER_ADD_PICUTURECOMMEND_NUM";
    /**
     * 用户添加帖子评论数量统计
     */
    public static final String USER_ADD_RECOMMENDCOMMEND_NUM = "LIVE_USER_ADD_RECOMMENDCOMMEND_NUM";
    /**
     * 用户添加帖子评论数量统计
     */
    public static final String USER_ADD_RECOMMENDFOLLOW_NUM = "LIVE_USER_ADD_RECOMMENDFOLLOW_NUM";


    public static final String PAY_SYSTEM_INFO = "LIVE_PAY_SYSTEM_INFO";
    public static final String PAY_FOR_SYSTEM_INFO = "LIVE_PAY_FOR_SYSTEM_INFO";

    // 六和大神
    public static final String LHC_GOD_TYPE = "LIVE_LHC_GOD_TYPE_";

    /**
     * 校验用户获取验证码次数 Redis key
     */
    public static final String USER_CAPTCHA_CHECK_PREFIX = "LIVE_USER_CAPTCHA_CHECK_";
    public static final String REDIS_CLEAR_TASK_KEY = "LIVE_REDIS_CLEAR_TASK_KEY";

    public static final String METHOD_STAT_ENABLE = "LIVE_METHOD_STAT_ENABLE_";
    public static final String METHOD_STAT_PREFIX = "LIVE_METHOD_STAT_";
    public static final String METHOD_STAT_IP_PREFIX = "LIVE_METHOD_IP_STAT_";
    public static final String METHOD_STAT_TIME_PREFIX = "LIVE_METHOD_TIME_STAT_";
    public static final String METHOD_STAT_TIME_IP_PREFIX = "LIVE_METHOD_IP_TIME_STAT_";
    public static final String METHOD_STAT_TOTAL_IP_PREFIX = "LIVE_METHOD_IP_TOTAL_STAT_";

    /**
     * 登录校验REDIS KEY
     */
    public static final String PREFIX_ACCOUNT = "LIVE_MEMBER_ACCOUNT_";
    public static final String BLOCK_PREFIX_ACCOUNT = "LIVE_BLOCK_MEMBER_ACCOUNT_";
    public static final String LOGIN_ACCOUNT_TIME_PREFIX = "LIVE_LOGIN_ACCOUNT_TIME_";
    public static final String CAPTCHA_FLAG = "LIVE_CAPTCHA_FLAG_ACCOUNT_";

    public static final String OFFLINE_PAYMENT = "LIVE_OFFLINE_PAYMENT";

    // 缓存大神类型集合
    public static final String LHC_GOD_TYPE_COLLECTION = "LIVE_GOD_TYPE_COLLECTIONS";

    // 缓存数据等级集合
    public static final String DATA_VALUE_LEVEL_CACHE = "LIVE_DATA_VALUE_LEVEL_CACHE";

    // 缓存后台用户信息
    public static final String LHC_REFERRER_ALL = "LIVE_LHC_REFERRER_ALL";

    // 缓存后台用户ID和name
    public static final String LHC_REFERRER_ALL_ID_NAME = "LIVE_LHC_REFERRER_ALL_ID_NAME";

    /**
     * 心水对于APP游客缓存结果数据 KEY
     */
    public static final String APP_TOURISTS_HALL_RECOMEND_DATA = "LIVE_APP_TOURISTS_HALL_RECOMEND_";
    public static final String APP_TOURISTS_ESSENCE_RECOMEND_DATA = "LIVE_APP_TOURISTS_ESSENCE_RECOMEND_";
    public static final String APP_TOURISTS_HOT_RECOMEND_DATA = "LIVE_APP_TOURISTS_HOT_RECOMMEND_";
    public static final String APP_TOURISTS_TODAY_RECOMEND_DATA = "LIVE_APP_TOURISTS_TODAY_RECOMMEND_";

    public static final String APP_CUSTOMER_HALL_RECOMEND_DATA = "LIVE_APP_CUSTOMER_HALL_RECOMMEND_";
    public static final String APP_CUSTOMER_ESSENCE_RECOMEND_DATA = "LIVE_APP_CUSTOMER_ESSENCE_RECOMMEND_";
    public static final String APP_CUSTOMER_HOT_RECOMEND_DATA = "LIVE_APP_CUSTOMER_HOT_RECOMMEND_";
    public static final String APP_CUSTOMER_TODAY_RECOMEND_DATA = "LIVE_APP_CUSTOMER_TODAY_RECOMMEND_";

    /**
     * 心水对于WEB游客缓存结果数据 KEY
     */
    public static final String WEB_TOURISTS_HALL_RECOMEND_DATA = "LIVE_WEB_TOURISTS_HALL_RECOMMEND_";
    public static final String WEB_TOURISTS_ESSENCE_RECOMEND_DATA = "LIVE_WEB_TOURISTS_ESSENCE_RECOMMEND_";
    public static final String WEB_TOURISTS_HOT_RECOMEND_DATA = "LIVE_WEB_TOURISTS_HOT_RECOMMEND_";
    public static final String WEB_TOURISTS_TODAY_RECOMEND_DATA = "LIVE_WEB_TOURISTS_TODAY_RECOMMEND_";

    public static final String WEB_CUSTOMER_HALL_RECOMEND_DATA = "LIVE_WEB_CUSTOMER_HALL_RECOMMEND_";
    public static final String WEB_CUSTOMER_ESSENCE_RECOMEND_DATA = "LIVE_WEB_CUSTOMER_ESSENCE_RECOMMEND_";
    public static final String WEB_CUSTOMER_HOT_RECOMEND_DATA = "LIVE_WEB_CUSTOMER_HOT_RECOMMEND_";
    public static final String WEB_CUSTOMER_TODAY_RECOMEND_DATA = "LIVE_WEB_CUSTOMER_TODAY_RECOMMEND_";

    // 缓存投票信息
    public static final String LHC_RVOTES_ALL = "LIVE_LHC_RVOTES_ALL";

    public static final String MEMBER_ENROLL_ACCOUNT_PREFIX = "LIVE_MEMBER_ENROLL_ACCOUNT_";
    public static final String BINDING_MOBILE_PREFIX = "LIVE_BINDING_MOBILE_";
    public static final String USER_ACCOUNT_SENSITIVE_CASE = "LIVE_USER_ACCOUNT_SENSITIVE_CASE";
    public static final String REGISTER_ACCOUNT_CAN_RECOMMEND = "LIVE_REGISTER_ACCOUNT_CAN_RECOMMEND";
    public static final String REGISTER_ACCOUNT_CAN_RECOMMEND_INFO = "LIVE_REGISTER_ACCOUNT_CAN_RECOMMEND_INFO";

    //对指定用户禁用指定接口
    public static final String DISABLE_FUNCTION_USER_PREFIX = "LIVE_DISABLE_FUNCTION_USER_";
    //对指定用户禁用所有接口
    public static final String DISABLE_ALL_FUNCTION_USER_PREFIX = "LIVE_DISABLED_ALL_FUNCTION_USER_";
    //对所有用户禁用指定接口
    public static final String DISABLE_FUNCTION_PREFIX = "LIVE_DISABLE_FUNCTION_";
    //对所有用户禁用所有接口
    public static final String DISABLE_ALL_FUNCTIONS = "LIVE_DISABLED_ALL_FUNCTIONS";
    //接口白名单，不需要进行登录校验
    public static final String WHITE_URIS = "LIVE_WHITE_URIS";
    /**
     * 需要绑定手机号的uri
     */
    public static final String MUST_MOBILE_BINDING_URIS = "LIVE_MUST_MOBILE_BINDING_URIS";
    /**
     * 注册型用户需要校验权限的uri
     */
    public static final String HAS_AUTH_BY_REGISTER_USER_URIS = "LIVE_HAS_AUTH_BY_REGISTER_USER_URIS";

    // 心水广告返回缓存
    public static final String XS_ADVERTISING_CACHE = "LIVE_XS_ADVERTISING_CACHE";

    // 圈规缓存
    public static final String CIRCLE_RULE_STRING = "LIVE_CIRCLE_RULE_STRING";
    // 投票缓存
    public static final String ADDVOTESLIMITNUM_STRING = "LIVE_ADDVOTESLIMITNUM_STRING_";

    /**
     * 浪聊所有聊天室列表
     */
    public static final String THIRD_ALL_CHAT_GROUP_LIST = "LIVE_THIRD_ALL_CHAT_GROUP_LIST";

    /**
     * app端浪聊聊天室返回列表
     */
    public static final String THIRD_ALL_CHAT_GROUP_VO_LIST = "LIVE_THIRD_ALL_CHAT_GROUP_VO_LIST";

    /**
     * 美恰聊天室返回列表
     */
    public static final String MEIQIA_ALL_CHAT_VO_LIST = "LIVE_MEIQIA_ALL_CHAT_VO_LIST";

    /**
     * 所有私聊聊天室列表
     */
    public static final String THIRD_CHAT_PRIVATE_GROUP_LIST = "LIVE_THIRD_CHAT_PRIVATE_GROUP_LIST";

    /**
     * 私聊聊天室Vo返回列表
     */
    public static final String THIRD_PRIVATE_CHAT_GROUP_VO_LIST = "LIVE_THIRD_PRIVATE_CHAT_GROUP_VO_LIST";

    public static final String AGENT_SETTLE = "LIVE_AGENT_SETTLE_";
    /**
     * 审核开关
     */
    public static final String AUDIT_STATUS = "LIVE_AUDIT_STATUS";
    /**
     * 打赏开关
     */
    public static final String REWARD_STATUS = "LIVE_REWARD_STATUS";
    /**
     * 代理用户的结算数据刷新日期
     */
    public static final String AGENT_DATA_DATE = "LIVE_AGENT_DATA_DATE_";
    /**
     * 代理用户的月收益走势
     */
    public static final String AGENT_MONTH_INCOME = "LIVE_AGENT_MONTH_INCOME_";
    /**
     * 代理用户返水贡献排行版
     */
    public static final String AGENT_BACKWATER_TOP = "LIVE_AGENT_BACKWATER_TOP_";
    /**
     * 代理团队成员月走势
     */
    public static final String AGENT_MEMBER_MONTH_DETAIL = "LIVE_AGENT_MEMBER_MONTH_";
    /**
     * 每月新增成员
     */
    public static final String AGENT_MEMBER_NEW_NUM = "LIVE_AGENT_MEMBER_NEW_NUM_";
    /**
     * 代理英雄排行榜
     */
    public static final String AGENT_HERO_SORT = "LIVE_AGENT_HERO_SORT";
    /**
     * 代理详情
     */
    public static final String AGENT_MEMBER_DETAIL = "LIVE_AGENT_MEMBER_DETAIL_";
    /**
     * 代理详情下载次数限制
     */
    public static final String AGENT_DOWN_LIMIT = "LIVE_AGENT_DOWN_LIMIT";

    public static final String APP_KEY = "LIVE_APP_KEY_";
    // 下三路单双数据
    public static final String THREE_WAY_SIGLE_DOUBLE = "LIVE_THREE_WAY_SIGLE_DOUBLE_";
    // 下三路大小数据
    public static final String THREE_WAY_BIG_SMALL = "LIVE_THREE_WAY_BIG_SMALL_";
    // 下三路问路数据
    public static final String THREE_WAY_ASK_DATA = "LIVE_THREE_WAY_ASK_DATA_";
    // 首页中间广告数据
    public static final String PORAL_HEAD_MID_ADPRO = "LIVE_PORAL_HEAD_MID_ADPRO_";

    //活动相关缓存key
    public static final String AD_SITE_CACHE_LIST = "LIVE_AD_SITE_CACHE_LIST";
    public static final String AD_SITE_CACHE_CATEGORY = "LIVE_AD_SITE_CACHE_CATEGORY";

    public static final String AD_BASIC_CACHE_ITEM = "LIVE_AD_BASIC_CACHE_ITEM_";
    public static final String AD_BASIC_CACHE_LIST = "LIVE_AD_BASIC_CACHE_LIST";

    public static final String AD_PHOTO_CACHE_ITEM = "LIVE_AD_PHOTO_CACHE_ITEM_";
    public static final String AD_PHOTO_CACHE_LIST = "LIVE_AD_PHOTO_CACHE_LIST";

    public static final String OPERATER_ROLE_ITEM = "LIVE_OPERATER_ROLE_ITEM_";
    public static final String APP_VEST_BAG = "LIVE_APP_VEST_BAG";

    //历史赛果数据缓存key
    //重庆时时彩-历史赛果数据缓存key
    public static final String CQSSC_SG_HS_LIST = "LIVE_CQSSC_SG_HS_LIST";
    //新疆时时彩-历史赛果数据缓存key
    public static final String XJSSC_SG_HS_LIST = "LIVE_XJSSC_SG_HS_LIST";
    //天津时时彩-历史赛果数据缓存key
    public static final String TJSSC_SG_HS_LIST = "LIVE_TJSSC_SG_HS_LIST";
    //10分时时彩-历史赛果数据缓存key
    public static final String TENSSC_SG_HS_LIST = "LIVE_TENSSC_SG_HS_LIST";
    //5分时时彩-历史赛果数据缓存key
    public static final String FIVESSC_SG_HS_LIST = "LIVE_FIVESSC_SG_HS_LIST";
    //德州时时彩-历史赛果数据缓存key
    public static final String JSSSC_SG_HS_LIST = "LIVE_JSSSC_SG_HS_LIST";
    //德州六合彩-历史赛果数据缓存key
    public static final String ONELHC_SG_HS_LIST = "LIVE_ONELHC_SG_HS_LIST";
    //5分六合彩-历史赛果数据缓存key
    public static final String FIVELHC_SG_HS_LIST = "LIVE_FIVELHC_SG_HS_LIST";
    //时时六合彩-历史赛果数据缓存key
    public static final String AMLHC_SG_HS_LIST = "LIVE_AMLHC_SG_HS_LIST";
    //北京PK-历史赛果数据缓存key
    public static final String BJPKS_SG_HS_LIST = "LIVE_BJPKS_SG_HS_LIST";
    //10分PK-历史赛果数据缓存key
    public static final String TENPKS_SG_HS_LIST = "LIVE_TENPKS_SG_HS_LIST";
    //5分PK-历史赛果数据缓存key
    public static final String FIVEPKS_SG_HS_LIST = "LIVE_FIVEPKS_SG_HS_LIST";
    //极速PK-历史赛果数据缓存key
    public static final String JSPKS_SG_HS_LIST = "LIVE_JSPKS_SG_HS_LIST";
    //幸运飞艇-历史赛果数据缓存key
    public static final String XYFT_SG_HS_LIST = "LIVE_XYFT_SG_HS_LIST";
    //PCEGG-历史赛果数据缓存key
    public static final String PCEGG_SG_HS_LIST = "LIVE_PCEGG_SG_HS_LIST";
    //腾讯分分彩-历史赛果数据缓存key
    public static final String TXFFC_SG_HS_LIST = "LIVE_TXFFC_SG_HS_LIST";
    //大乐透-历史赛果数据缓存key
    public static final String TCDLT_SG_HS_LIST = "LIVE_TCDLT_SG_HS_LIST";
    //体彩排列五-历史赛果数据缓存key
    public static final String TCPLW_SG_HS_LIST = "LIVE_TCPLW_SG_HS_LIST";
    //体彩7星彩-历史赛果数据缓存key
    public static final String TC7XC_SG_HS_LIST = "LIVE_TC7XC_SG_HS_LIST";
    //福彩双色球-历史赛果数据缓存key
    public static final String FCSSQ_SG_HS_LIST = "LIVE_FCSSQ_SG_HS_LIST";
    //福彩3D-历史赛果数据缓存key
    public static final String FC3D_SG_HS_LIST = "LIVE_FC3D_SG_HS_LIST";
    //福彩7乐彩-历史赛果数据缓存key
    public static final String FC7LC_SG_HS_LIST = "LIVE_FC7LC_SG_HS_LIST";
    //澳洲牛牛/澳洲pks-历史赛果数据缓存key
    public static final String AUSPKS_SG_HS_LIST = "LIVE_AUSPKS_SG_HS_LIST";
    //极速pk番摊-历史赛果数据缓存key
    public static final String JSPKS_FT_SG_HS_LIST = "LIVE_JSPKS_FT_SG_HS_LIST";
    //幸运飞艇番摊-历史赛果数据缓存key
    public static final String XYFT_FT_SG_HS_LIST = "LIVE_XYFT_FT_SG_HS_LIST";
    //极速时时彩番摊-历史赛果数据缓存key
    public static final String JSSSC_FT_SG_HS_LIST = "LIVE_JSSSC_FT_SG_HS_LIST";
    //澳洲ACT-历史赛果数据缓存key
    public static final String AUSACT_SG_HS_LIST = "LIVE_AUSACT_SG_HS_LIST";
    //澳洲时时彩-历史赛果数据缓存key
    public static final String AUSSSC_SG_HS_LIST = "LIVE_AUSSSC_SG_HS_LIST";
    //六合彩-历史赛果数据缓存key
    public static final String LHC_SG_HS_LIST = "LIVE_LHC_SG_HS_LIST";

    //澳洲快三-历史赛果数据缓存key
    public static final String AZKS_SG_HS_LIST = "LIVE_AZKS_SG_HS_LIST";
    //德州快三-历史赛果数据缓存key
    public static final String DZKS_SG_HS_LIST = "LIVE_DZKS_SG_HS_LIST";
    //德州PC蛋蛋-历史赛果数据缓存key
    public static final String DZPCEGG_SG_HS_LIST = "LIVE_DZPCEGG_SG_HS_LIST";
    //德州幸运飞艇-历史赛果数据缓存key
    public static final String DZXYFT_SG_HS_LIST = "LIVE_DZXYFT_SG_HS_LIST";
    //新加坡六合彩-历史赛果数据缓存key
    public static final String XJPLHC_SG_HS_LIST = "LIVE_XJPLHC_SG_HS_LIST";
    // 大神计划父类彩种类别
    public static final String GOD_PLAN_PARENT_LOTTERY_CATEGORY = "LIVE_GOD_PLAN_PARENT_LOTTERY_CATEGORY";
    // 大神计划父类彩种类别
    public static final String GOD_PLAN_CHILD_LOTTERY_CATEGORY = "LIVE_GOD_PLAN_CHILD_LOTTERY_CATEGORY";
    // 大神计划同一系下所有彩种类别GOD_PLAN_DTO_LOTTERY
    public static final String GOD_PLAN_SERIES_LOTTERY_CATEGORY = "LIVE_GOD_PLAN_SERIES_LOTTERY_CATEGORY";
    // 大神计划彩种类别
    public static final String GOD_PLAN_LOTTERY_CATEGORY = "LIVE_GOD_PLAN_LOTTERY_CATEGORY";
    // 大神计划彩种系列名称
    public static final String GOD_PLAN_LOTTERY_SERIES = "LIVE_GOD_PLAN_LOTTERY_SERIES";
    public static final String GOD_PLAN_LOTTERY_PLAY = "LIVE_GOD_PLAN_LOTTERY_PLAY";
    public static final String GOD_PLAN_LOTTERY_PLAY_LOTTERY = "LIVE_GOD_PLAN_LOTTERY_PLAY_LOTTERY";
    public static final String GOD_PLAN_DTO_LOTTERY = "LIVE_GOD_PLAN_DTO_LOTTERY_";
    public static final String GOD_PLAN_LOTTERY = "LIVE_GOD_PLAN_LOTTERY_";
    public static final String GOD_PLAN_SERIES_PLAY_LOTTERY = "LIVE_GOD_PLAN_SERIES_PLAY_LOTTERY_";
    public static final String GOD_PLAN_PLAY_SETTING_LOTTERY_GOD = "LIVE_GOD_PLAN_PLAY_SETTING_LOTTERY_GOD_";
    public static final String GOD_PLAN_LOTTERY_BETLIST = "GOD_PLAN_LOTTERY_BETLIST";
    public static final String MEMBER_DEVICE_CALC = "LIVE_MEMBER_DEVICE_CALC";
    public static final String MEMBER_YOUKE_CALC = "LIVE_MEMBER_YOUKE_CALC";
    public static final String MEMBER_ONLINE_CALC = "LIVE_MEMBER_ONLINE_CALC";
    public static final String RECHARGE_ACCOUNT_LITTLE_VO_LIST = "LIVE_RECHARGE_ACCOUNT_LITTLE_VO_LIST";
    public static final String PAY_FOR_LIST = "LIVE_PAY_FOR_LIST";


    //开元注单游戏名称缓存key
    public static final String KY_KIND_KEY = "LIVE_KY_KIND_KEY";
    //开元注单房间缓存key
    public static final String KY_SERVER_KEY = "LIVE_KY_SERVER_KEY";
    //ag 注单  游戏名称缓存 key
    public static final String AG_GANE_KEY = "LIVE_AG_GANE_KEY";
    //ag 注单  玩法名称缓存 key
    public static final String AG_PAY_KEY = "LIVE_AG_PAY_KEY";
    //ag 注单  平台名称缓存 key
    public static final String AG_PLATFORM_KEY = "LIVE_AG_PLATFORM_KEY";
    //ag 注单  大厅类型名称缓存 key
    public static final String AG_ROUND_KEY = "LIVE_AG_ROUND_KEY";
    //ae 注单  游戏名称缓存 key
    public static final String AE_GANE_KEY = "LIVE_AE_GANE_KEY";
    //ae 注单  房间名称缓存 key
    public static final String AE_ROOM_KEY = "LIVE_AE_ROOM_KEY";

    //mg TAG 游戏CODE缓存 key
    public static final String MG_GANE_KEY = "LIVE_MG_GANE_KEY";

    //mg 注单  游戏名称缓存 key
    public static final String MG_GANE_NAME_KEY = "LIVE_MG_GANE_NAME_KEY";

    //DB 捕鱼注单  游戏名称缓存 key
    public static final String DB_GANE_NAME_KEY = "LIVE_DB_GANE_NAME_KEY";

    //大神跟单 根据彩种ID缓存KEY
    public static final String GOD_PLAN_LOTTERY_CATEGORY_LOTTERY_KEY = "LIVE_GOD_PLAN_LOTTERY_CATEGORY_LOTTERY_KEY";
    //大神跟单 根据ID缓存KEY
    public static final String GOD_PLAN_LOTTERY_CATEGORY_ID_KEY = "LIVE_GOD_PLAN_LOTTERY_CATEGORY_ID_KEY";

    // 大神计划 大神ID缓存KEY
    public static final String GOD_PLAN_LOTTERY_ISSUE_GODID_KEY = "LIVE_GOD_PLAN_LOTTERY_ISSUE_GODID_KEY_";

    //大神计划 设置几期计划的缓存
    public static final String GOD_PLAN_QIHAO = "LIVE_GOD_PLAN_QIHAO";

    //大神计划 设置几期计划的缓存
    public static final String GOD_PLAN_OPENCLOSE = "LIVE_GOD_PLAN_OPENCLOSE";

    //后台大神页面缓存的63条历史数据
    public static final String GOD_PLAN_ISSUE_LIST = "LIVE_GOD_PLAN_ISSUE_LIST";

    public static final String SYSTEM_INFO_LIST = "LIVE_SYSTEM_INFO_LIST";

    //结算判断输赢
    public static final String WIN_OR_LOSE = "LIVE_WIN_OR_LOSE_";
    //大神推单
    public static final String GOD_PUSH_KEY = "LIVE_GOD_PUSH_ORDER_";

    // ========================登陆人数统计================================================
    public static final String ANDROIDONLINE = "LIVE_ANDONLINE";
    public static final String IOSONLINE = "LIVE_IOSONLINE";
    public static final String H5ONLINE = "LIVE_H5ONLINE";
    public static final String WEBONLINE = "LIVE_WEBONLINE";


    // ========================在线人数统计（包括登陆和游客的）根据ip================================================
    public static final String ANDROIDYOUKE = "LIVE_ANDYOUKE";
    public static final String IOSYOUKE = "LIVE_IOSYOUKE";
    public static final String H5YOUKE = "LIVE_H5YOUKE";
    public static final String WEBYOUKE = "LIVE_WEBYOUKE";

    // ========================在线人数统计（包括登陆和游客的）根据设备id================================================
    public static final String ANDROIDDEVICE = "LIVE_ANDDEVICE";
    public static final String IOSDEVICE = "LIVE_IOSDEVICE";
    public static final String H5DEVICE = "LIVE_H5DEVICE";
    public static final String WEBDEVICE = "LIVE_WEBDEVICE";

    //会员等级配置
    public static final String MEM_LEVEL_CONFIG = "LIVE_MEM_LEVEL_CONFIG";

    //直播间彩种
    public static final String LOTTERY_LIST_LIVE_KEY = "ONE_LIVE_LOTTERY_LIST_LIVE_KEY";

    //直播间默认彩种id
    public static final String LOTTERY_DEFAULT_LIVE_KEY = "ONE_LIVE_LOTTERY_DEFAULT_LIVE_KEY";

    /**
     * 返水设置表缓存key
     */
    public static final String RETURN_ALL_WATER_SET = "LIVE_RETURN_ALL_WATER_SET";
    /**
     * 第三方返水表缓存key
     */
    public static final String RETURN_ALL_THIRD_SET = "LIVE_RETURN_ALL_THIRD_SET";
    /**
     * 彩票返水表缓存key
     */
    public static final String RETURN_ALL_LOTTERY_SET = "LIVE_RETURN_ALL_LOTTERY_SET";

    /**
     * 系统是否处于维护状态
     */
    public static final String LIVE_SYSTEM_MAINTENANCE_STATUS = "LIVE_SYSTEM_MAINTENANCE_STATUS";
    /**
     * 管理后台是否处于维护状态
     */
    public static final String LIVE_SYSTEM_MANAGE_MAINTENANCE_STATUS = "LIVE_SYSTEM_MANAGE_MAINTENANCE_STATUS";

    /**
     * 用户等级对象key: key后拼接用户标识
     */
    public static final String APP_MEMBER_LEVEL = "LIVE_APP_MEMBER_LEVEL_";

    /**
     * 主播房间key: key后拼接房间ID
     */
    public static final String LIVE_TEN_PARAM_ROOM_DTO = "LIVE_TEN_PARAM_ROOM_DTO_";

    //=======================财务相关==================================================

    /**
     * 账变汇总
     */
    public static final String ACCOUNT_SUMMARY_VO = "account_summary_vo_";

    /**
     * 充提入款设定
     */
    public static final String LIVE_SYS_PAYSET = "LIVE_SYS_PAYSET_";

    /**
     * MG 游戏 注单时间
     */
    public static final String LIVE_MG_ORDER_RECORD_KEY = "LIVE_MG_ORDER_RECORD_KEY";

    public static final String LIVE_MAINTENANCE_WHITE_IP_LIST_KEY = "LIVE_MAINTENANCE_WHITE_IP_LIST_KEY";

    /**
     * 业务参数缓存
     */
    public static final String LIVE_BUS_PARAM_CODE = "LIVE_BUS_PARAM_CODE_";

    /**
     * 出帳訂單管理列表缓存
     */
    public static final String LIVE_MANAGE_INCARNATE_ORDER_LIST_PAGE = "LIVE_MANAGE_INCARNATE_ORDER_LIST_PAGE_";

    /**
     * 模糊匹配清除缓存key
     */
    public static final String LIVE_AD_SLOT = "LIVE_AD_SLOT_";


    /**
     * 用户关注数key
     */
    public static final String LIVE_APP_USER_ATTENTION = "LIVE_APP_USER_ATTENTION_";
    /**
     * 房间禁言缓存
     */
    public static final String LIVE_APP_BAS_FORBID = "LIVE_APP_BAS_FORBID_";

    /**
     * app最新更新数据key
     */
    public static final String LIVE_APP_LAST_UPDATEAPP = "LIVE_APP_LAST_UPDATEAPP";

    /**
     * 家族长key
     */
    public static final String LIVE_FAMILY = "LIVE_FAMILY_";

    /**
     * 家族成员key
     */
    public static final String LIVE_MEMFAMILYMEM = "LIVE_MEMFAMILYMEM_";

    /**
     * 家族提现管理
     */
    public static final String LIVE_FAMILY_WITHDRAWAL_MANAGEMENT = "LIVE_FAMILY_WITHDRAWAL_MANAGEMENT_";

    /**
     * 视频列表页缓存前缀
     */
    public static final String LIVE_APP_VIDEO_LIST = "LIVE_APP_VIDEO_LIST_";
    /**
     * 系统公告缓存前缀
     */
    public static final String LIVE_INF_SYS_NOTICE = "LIVE_INF_SYS_NOTICE_";
    /**
     * 直播间房间收入 房间ID
     */
    public static final String LIVE_ROOM_REVENUE_KEY = "LIVE_ROOM_REVENUE_";
    /**
     * 直播间房间火力值  房间ID
     */
    public static final String LIVE_ROOM_HEAT_KEY = "LIVE_ROOM_HEAT_";

    /**
     * 视频详情上一条下一条缓存前缀
     */
    public static final String LIVE_APP_VIDEO_DETAIL_LASTNEXT = "LIVE_APP_VIDEO_DETAIL_LASTNEXT_";


    public static final String LIVE_INF_SYSREMINDINFO_NUM = "LIVE_INF_SYSREMINDINFO_NUM";


    /**
     * 系统用户缓存前缀
     */
    public static final String LIVE_SYSTEM_USER = "LIVE_SYSTEM_USER_";


    /**
     * 角色缓存前缀
     */
    public static final String LIVE_SYSROLEFUNC = "LIVE_SYSROLEFUNC_";

    /**
     * 角色对应功能缓存前缀
     */
    public static final String LIVE_SYSFUNCTIONORG = "LIVE_SYSFUNCTIONORG_";

    /**
     * 用户收藏数缓存前缀
     */
    public static final String LIVE_APP_USER_FAVORITES_NUM = "LIVE_APP_USER_FAVORITES_NUM_";

    /**
     * 用户资源数缓存前缀
     */
    public static final String LIVE_APP_USER_RESOURCES_NUM = "LIVE_APP_USER_RESOURCES_NUM_";

    /**
     * 用户的上级关系前缀
     */
    public static final String LIVE_APP_USER_RELATIONSHIP = "LIVE_APP_USER_RELATIONSHIP_";

    /**
     * 用户申请主播信息前缀
     */
    public static final String LIVE_APP_USER_CERTIFICATION = "LIVE_APP_USER_CERTIFICATION_";

    public static final String LIVE_BAS_ANCHOR_PLATFORM_CONFIG_KEY = "LIVE_BAS_ANCHOR_PLATFORM_CONFIG_KEY_";

    /**
     * 房间禁言
     */
    public static final String LIVE_FORBID_SPEAK_LIST = "LIVE_FORBID_SPEAK_LIST_";

    /**
     * 房间禁播
     */
    public static final String LIVE_FORBID_NOT_IN_LIST = "LIVE_FORBID_NOT_IN_LIST_";
    public static final String LIVE_RENCI_ = "LIVE_RENCI_";


    /**
     * 不同等级对应的公司入款设定缓存
     */
    public static final String LIVE_APP_BANKS_LEVEL = "LIVE_APP_BANKS_LEVEL_";


    /**
     * 验证码发送记录
     */
    public static final String LIVE_SMS_CODE_DETAIL = "LIVE_SMS_CODE_DETAIL_";

    /**
     * redis拼接冒号（方便可视化查询）
     */
    public static final String LIVE_JOINT_COLON = ":";

    /**
     * 圈子粉丝和关注数缓存前缀
     */
    public static final String LIVE_APP_CIRCLE_FANS_FOCUS_NUMBER = "LIVE_APP_CIRCLE_FANS_FOCUS_NUMBER_";

    /**
     * 圈子列表页
     */
    public static final String LIVE_APP_CIRCLE_POST_LIST_ = "LIVE_APP_CIRCLE_POST_LIST_";

    /**
     * 圈子帖子总数
     */
    public static final String LIVE_APP_CIRCLE_POST_LIST_NUM = "LIVE_APP_CIRCLE_POST_LIST_NUM";

    /**
     * 注册总数
     */
    public static final String LIVE_APP_USER_ALL_REGISTERED_NUM = "LIVE_APP_USER_ALL_REGISTERED_NUM";

    /**
     * 角色接口权限
     */
    public static final String LIVE_ROLE_INTERFACES = "LIVE_ROLE_INTERFACES_";

    /**
     * 参数列表
     */
    public static final String LIVE_SYS_BUSPARAMETER_ARRAY = "LIVE_SYS_BUSPARAMETER_ARRAY_";
    /**
     * 公司入款订单列表
     */
    public static final String LIVE_RECHARGE_UNLINE_ORDER_LIST = "LIVE_RECHARGE_UNLINE_ORDER_LIST";

    /**
     * 支付方式id对应支付商名称缓存
     */
    public static final String LIVE_SYS_PAYPROVIDER_BY_TPAYSETID = "LIVE_SYS_PAYPROVIDER_BY_TPAYSETID_";

    /**
     * 线上入款訂單管理列表缓存
     */
    public static final String LIVE_MANAGE_ONLINE_ORDER_LIST_PAGE = "LIVE_MANAGE_ONLINE_ORDER_LIST_PAGE_";


    /**
     * 签到在线人数
     */
    public static final String CHECK_IN_PRE_KEY = "{online_users}:time";

    /**
     * 家族长的礼物和投注分成前缀
     */
    public static final String LIVE_MANAGE_FAMILY_GIFT_BET = "LIVE_MANAGE_FAMILY_GIFT_BET_";


}
