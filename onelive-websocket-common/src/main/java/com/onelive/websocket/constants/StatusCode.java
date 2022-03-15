package com.onelive.websocket.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 系统代码值
 * @time: 2020/10/1 14:25
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    SUCCESSCODE(200, "操作成功"),
    UNLEGAL(400, "请求不合法"),
    UNLOGIN_CODE(401, "用户未登录或已过期"),
    SYSTEM_ERROR(999, "网络繁忙，请稍后重试"),
    BUSINESS_ERROR(550, "操作失败"),
    PARAM_ERROR(551, "参数有误，请检查!"),
    IP_ERROR(552, "请联系管理员添加服务白名单"),

    ////////////////////业务参数 以1000开始///////////////////////
    REGISTER_BLANK_AREACODE(1000, "请选择区号"),
    REGISTER_BLANK_PHONE(1001, "请填写手机号"),
    REGISTER_BLANK_PASSWORD(1002, "请输入密码"),
    REGISTER_BLANK_CONFIRMPASSWORD(1003, "请输入确认密码"),
    REGISTER_BLANK_SMSCODE(1004, "请填写短信验证码"),
    REGISTER_BLANK_IMGCODE(1005, "请填写图片验证码"),
    REGISTER_WRONG_IMG(1006, "图片验证失败"),
    REGISTER_DIFF_PASSWORD(1007, "两次密码填写不一样!"),
    REGISTER_EXISTS_PHONE(1008, "该手机号已注册"),
    REGISTER_EXISTS_ANCHOR(100801, "该主播账号已注册"),
    REGISTER_ANCHOR_LOSE(100802, "主播信息缺失"),
    REGISTER_COUNTRY_LOSE(100803, "获取所在地区信息失败"),
    REGISTER_UNSELECTED_USER_COUNTRY(100804, "未选择用户地区"),

    LOGIN_PARAM_ERROR(1009, "登录参数有误"),
    LOGIN_NOEXISTS_ACCOUNT(1010, "账号不存在"),
    LOGIN_WRONG_PASSWORD(1011, "密码错误"),
    LOGIN_FROZEN(1012, "账号已被冻结"),
    LOGIN_LOCK_PASSWORD(101201, "账号已被锁定，1小时后解封"),
    LOGIN_RESET_WAY(101202, "账号已经绑定手机，请进行使用手机号登陆"),

    RESET_EMPTY_PASSWORD(1013, "请填写密码！"),
    RESET_PARAM_ERROR(1014, "重置密码参数有误"),
    RESET_NO_USER_ERROR(1015, "用户不存在"),

    /** 直播相关 */
    ANCHOR_ID_ISNULL(1150, "主播userId为空"),
    LIVE_TITLE_ISNULL(1151, "直播间标题为空"),
    LIVE_STUDIONUM_ISNULL(1152, "直播号为空"),
    GIFT_HOSTID_ISNULL(1153, "礼物接收人为空"),
    GIFT_GIFTID_ISNULL(1154, "礼物id为空"),
    BARRAGE_CONTENT_ISNULL(1155, "弹幕内容为空"),
    FOCUS_CANT_SELF(1156, "自己不能关注自己"),
    BUSERID_CANT_NULL(1157, "被踢人id不能为空"),
    NOT_ROOM_ADMIN_NULL(1158, "当前用户不是直播间管理员"),
    NOT_ROOM_ADMIN_KICKING(1158, "管理员不能踢管理员"),
    BANNED_BUSERID_CANT_NULL(1159, "被禁言人id不能为空"),
    NOT_ROOM_ADMIN_BANNED(1160, "管理员不能禁言管理员"),
    NOT_NULL_DEVICETYPE(1161, "deviceType不能为空"),
    STUDIO_ALREADY_CLOSE(1162, "当前直播间已经关闭"),
    UNSATISFY_GET_ANCHOR(1163, "不满足获取主播名片的条件"),
    UNUSE_GIFT_STATUS(1164, "当前礼物不可赠送"),

    PHONE_VALID(1052, "无效的手机号码"),

    //    ACCLOGIN_BLANK_ERROR(1000, "请填写账号!"),
//    ACCLOGIN_REGEX_ERROR(1001, "账号不合法"),
//    PASSWORD_BLANK_ERROR(1002, "请输入密码"),
//    PASSWORD_DIFF_ERROR(1003, "两次输入的密码不正确"),
//    ACCLOGIN_EXISTS(1004, "账户已存在"),
//    LOGIN_PARAM_ERROR(1005, "请输入账号或者密码"),
//    ACCLOGIN_NOT_EXISTS(1006, "账户不存在"),
//    ACCLOGIN_ACCOUNT_OR_PASSWORD(1022, "账号或密码错误"),
//    ACCLOGIN_PASSWORD_WRONG(1007, "密码错误"),
//    ACCLOGIN_FREEZE(1008, "账号已被冻结，请联系客服"),
//    ACCLOGIN_DELETE(1009, "账号异常，请联系客服"),
//    FORGET_NOBIND_PHONE(1010, "未绑定手机号码"),
//    FORGET_DIMATCH(1011, "账号和手机号不匹配"),
//    RESET_EMPTY_PASSWORD(1012, "请填写密码！"),
//    RESET_EMPTY_CONFIRM_PASSWORD(1013, "请填写确认密码!"),
//    RESET_PASSWORD_DIMATCH(1014, "两次密码填写不一样!"),
//    BINDPHONE_ONLY(1015, "手机号只能绑定一次!"),
//    PHONE_EMPTY(1016, "手机号不能为空"),
//    PHONECODE_EMPTY(1017, "手机验证码不能为空"),
//    CHANGE_OLD_PASSWORD(1018, "原密码不能为空!"),
//    CHANGE_NEW_PASSWORD(1019, "新密码不能为空!"),
//    CHANGE_CONFIRM_PASSWORD(1020, "确认密码不能为空!"),
//    CHANGE_PASSWORD_DIMATCH(1021, "两次密码填写不一样!"),
//    GOLDCHANGE_ERROR(1022, "账变异常"),
//    CHANGE_PASSWORD_SAME(1023, "原密码与新密码相同！"),
//    UPDATE_BET_ORDER_ERROR(1024, "更新注单状态异常"),
    //上传
    UPLOAD_ERROR_2000(2000, "上传失败"),
    UPLOAD_ERROR_2001(2001, "上次文件不能为空！"),
    UPLOAD_ERROR_2002(2002, "请检查文件格式！"),
    UPLOAD_ERROR_2003(2003, "图片大小不能超过2M"),
    UPLOAD_ERROR_2004(2004, "最多9张图片"),

    //短信
    SMS_CODE_ERROR(2005, "验证码错误"),
    SMS_CODE_FAILED(2006, "验证码已失效"),
    SMS_PHONE_FAILED(2007, "无效的手机号码"),
    SMS_PHONE_LIMIT(2008, "今天短信条数超过限制"),
    SMS_PHONE_EMPTY(2009, "请输入手机号"),

    //图片验证码
    IMG_CODE_ERROR(3001, "图片验证失败"),

    //账变,
    CHANGE_BALANCE_LACK(2009, "钱包余额不足"),

    //账变,
    NAME_REPEAT(4101, "名字重复"),

    LIVE_ROOM_BET_ERROR(500101,"下注异常"),
    LIVE_ROOM_BET_STATUS(500102,"房间未上线"),

    USER_NOT_BET(500103,"你的账户已被限制下注"),
    USER_FROZEN(500104,"你的账户已被限制冻结"),
    CREATE_INVITE_CODE_FAILURE(500105,"生成邀请码失败"),
    INVALID_INVITE_CODE(500105,"无效的邀请码"),
    MODIFY_NICK_NAME_NOT_FREE(500106,"免费修改昵称次数已用完"),

    LOTTERY_CATEGORY_EXISTS(500106,"彩票分类编号已存在"),

    LOTTERY_EXISTS(500107,"彩种编号已存在"),
    LOTTERY_NOT_EXISTS(500108,"彩种信息不存在"),
    PLAY_SETTING_NOT_EXISTS(500109,"玩法设置不存在"),
    PLAY_ODDS_NOT_EXISTS(500110,"赔率列表不存在"),
    PLAY_KILL_NOT_EXISTS(500111,"杀号配置不存在"),

    ;

    private Integer code;
    private String msg;

}
