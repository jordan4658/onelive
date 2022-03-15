package com.onelive.websocket.constants;

public interface WebSocketConstant {

	Integer MSG_TYPE_TEXT = 1;// 文本
	Integer MSG_TYPE_HTML = 2;// html
	Integer MSG_TYPE_FILE = 3;// 文件
	Integer MSG_TYPE_VOICE = 4;// 语音
	Integer MSG_TYPE_IMG = 5;// 图片
	Integer MSG_TYPE_JSON = 6;// JSON

	Integer CHAT_TYPE_SIGNLE = 1;// 单聊
	Integer CHAT_TYPE_GROUP = 2;// 群聊
	Integer CHAT_TYPE_SYS = 3;// 系统消息
	Integer CHAT_TYPE_TAGUSER = 4;// 用户@用户的消息

	String SENDER_TYPE_VISITOR = "visitor";
	String SENDER_TYPE_USER = "user";
	String SENDER_TYPE_ADMIN = "admin";

	String device_type_app = "app";
	String device_type_web = "web";

	Integer CHAT_ROOM_TYPE_TEMP = 1; // 临时群
	Integer CHAT_ROOM_TYPE_FIX = 2; // 固定群

	// 禁言标识
	public final static int banned = 0;

	// 踢人标识
	public final static int kicking = 1;

	// 取消关注标识
	public final static String cancel_focus = "cancel_focus";

	// 关注标识
	public final static String focus = "focus";

	// 方法请求post标识
	public final static String request_Type_post = "POST";

	// 方法请求get标识
	public final static String request_type_get = "GET";

	// websocket心跳链接 标识
	public final static String xin_tiao = "xinTiao";

	// 游客角色标识
	public final static String tourists = "tourists";

	// 日期格式
	public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	// 主播默认提现比列
	public final static String dict_code_zbDefaultTxbl = "zbDefaultTxbl";

	// app推荐H5链接
	public final static String dict_code_app_tuijian_h5_url = "app_tuijian_h5_url";
	// 消息栏目类型code
	public final static String dict_code_message_type = "message_type";

	// 直播广场打烊内容
	public final static String dict_code_closingTimeContent = "closingTimeContent";

	// 消息类型：礼物
	public final static Integer GIFT_MESSAGE = 4;

	// 消息类型：打赏
	public final static Integer EXCEPTIONAL_MESSAGE = 7;

	// 消息类型：一键打赏
	public final static Integer AKEY_EXCEPTIONAL_MESSAGE = 8;

	// 消息类型：弹幕
	public final static Integer BARRAGE_MESSAGE = 6;

	// 商品类型：礼物商品
	public final static Integer GIFT_GOODSTYPE = 1;
	// 商品类型：弹幕
	public final static Integer BARRAGE_GOODSTYPE = 2;
	// 商品类型：打赏商品
	public final static Integer EXCEPTIONAL_GOODSTYPE = 3;
	// 商品类型：其他商品
	public final static Integer OTHER_GOODSTYPE = 4;
	// 商品类型：一键打赏商品
	public final static Integer AKEY_EXCEPTIONAL_BARRAGE_GOODSTYPE = 5;
	// 商品类型：快速送礼商品
	public final static Integer QUICK_SHOWER_GOODSTYPE = 6;

	// 主播角色标识
	public final static String HOST_ROLE_VALUE = "host";
	// 普通用户角色标识
	public final static String USER_ROLE_VALUE = "user";
	// 僵尸用户角色标识
	public final static String ZOMBIES_USER_ROLE_VALUE = "zombies_user";
	// 测试用户角色标识
	public final static String TEST_ROLE_VALUE = "test";

	// 直播间管理员角色标识
	public final static String LIVE_ADMIN_ROLEVALUE = "live_super_admin";

	// response.setHeader中保存 用户登录token信息key：
	public final static String Authorization = "Authorization";

	// 每个用户进入直播间系统公告消息内容
	public final static String sys_announcement_chat = "我们提倡绿色直播，严禁涉政、涉恐、涉群体性事件、涉黄等直播内容";

}
