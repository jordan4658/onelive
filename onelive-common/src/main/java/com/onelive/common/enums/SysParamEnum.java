package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName : SysParamEnum
 * @Description : 系统参数配置枚举
 */
@Getter
@AllArgsConstructor
public enum SysParamEnum {
    ASTRICT_IP_GROUP("ASTRICT_IP_GROUP", "限制的ip"),
    SESSION_TIME_BACK("SESSION_TIME_BACK", "后台token有效期"),
    LOGO_PC("LOGO_PC", "pc端logo地址"),
    LOGO_MB("LOGO_MB", "移动端logo地址"),
    LOGO_MANAGE_LOGIN("LOGO_MANAGE_LOGIN", "后台登录页logo地址"),
    SIGN_CHECK_SWITCH("SIGN_CHECK_SWITCH", "0关1开"),
    SIGN_CHECK_PASS("SIGN_CHECK_PASS", "万能签名值"),
    SIGN_CHECK_EXPIRED("SIGN_CHECK_EXPIRED", "验签失效时长,单位秒"),
    GOOGLE_VERIFY_SWITCH("GOOGLE_VERIFY_SWITCH", "谷歌验证码开关,true开启false关闭"),
    MEM_FEEDBACK_SWITCH("MEM_FEEDBACK_SWITCH", "会员反馈功能开关"),
    LOTTERY_VERSION_ZIP_URL("LOTTERY_VERSION_ZIP_URL", "彩种压缩包路径"),
    REGISTER_MEMBER_ODDS("REGISTER_MEMBER_ODDS", "注册用户赔率"),
    LOTTERY_NOTICE_CONFIG("LOTTERY_NOTICE_CONFIG", "彩票公告配置"),
    APP_ROUTE_ROOT("APP_ROUTE_ROOT", "APP页面跳转路由根地址"),
    ;

    /**
     * 系统参数的代码
     */
    private String code;

    /**
     * 方便coder理解code的意思，无其它意义
     */
    private String remark;

}

