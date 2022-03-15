package com.onelive.manage.common.constant;

/**
 * @ClassName SystemRedisKeys
 * @Desc 系统缓存key值
 * @Date 2021/3/26 9:44
 */
public class SystemRedisKeys {

    /** 角色缓存前缀 */
    public static final String SYS_ROLE_FUNC = "SYSTEM:SYS_ROLE_FUNC:";

    /** 角色对应功能缓存前缀 */
    public static final String SYS_FUNCTION = "SYSTEM:SYS_FUNCTION:";

    /** 系统用户缓存对象key: key后拼接sfunid 前缀 */
    public static final String SYS_FUNCTION_FUN_ID = "SYSTEM:SYS_FUNCTION_FUN_ID:";

    /** 系统用户缓存列表对象key: key后拼接parsfunid  前缀 */
    public static final String SYS_FUNCTION_PARS_FUN_ID = "SYSTEM:SYS_FUNCTION_PARS_FUN_ID:";

    /** 系统用户key: key后拼接phoneno  前缀 */
    public static final String USER_PHONE_NO = "SYSTEM:USER_PHONE_NO:";

    /** 系统用户缓存前缀 */
    public static final String SYSTEM_USER = "SYSTEM:SYSTEM_USER:";

    /** 角色接口权限前缀 */
    public static final String ROLE_INTERFACES = "SYSTEM:ROLE_INTERFACES:";

    /** 登录token前缀 */
    public static final String TOKEN = "TOKEN:MANAGE:";

    /** 登录账号前缀 */
    public static final String LOGIN_USERINFO = "USERINFO:MANAGE:";

    /** ip白名单 */
    public static final String IP_WHITE = "SYSTEM:IP:";

    /** 线下订单确认 锁前缀 */
    public final static String PAY_MANAGE_OFFLINE_CONFIRM = "PAY:MANAGE:OFFLINE_CONFIRM:";

    /** 手动加减款/手动加减码 锁前缀 */
    public final static String PAY_MANAGE_ARTIFICIAL_ADD_SUB_AMT_DML = "PAY:MANAGE:ARTIFICIAL_ADD_SUB_AMT_DML:";

    /** 确认提现 锁前缀 */
    public final static String PAY_MANAGE_WITHDRAW_AMT = "PAY:MANAGE:WITHDRAW_AMT:";



    //*********************************************以上是含有前缀*********************************************
    //*********************************************以下是固定的key值******************************************


}    
    