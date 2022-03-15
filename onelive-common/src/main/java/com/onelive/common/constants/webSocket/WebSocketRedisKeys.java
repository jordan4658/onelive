package com.onelive.common.constants.webSocket;

public class WebSocketRedisKeys {

	/** 踢人时长（5分钟）单位：秒  TODO: 读取数据库  */
	public final static Long kicking_time = (long) (5 * 60);
	
	/** 房间踢人 key  */
	public final static String kicking_room_key = "room:kicking:";
	
	/** 房间禁言 key  */
	public final static String banned_room_key = "room:banned:";

	/** 直播间登录用户信息 key */
	public final static String user_list = "room:user_list:";
	
	/** redis 主播直播间key */
	public final static String anchor_studioNum = "room:studioNum:";
	
	/** 用户的直播间浏览历史*/
	public final static String studioNum_browse_history = "studioNum:browse_history:";
	
	/** 直播间进入用户的人次: 用户进入房间统计次数，一个用户只会被统计一次*/
	public final static String studioNum_enter_times = "room:enter_times:";
	
	/** 直播间热度 */
	public final static String studioNum_Heat = "room:heat:";
	
	/** 直播间试看超时用户id */
	public final static String studioNum_Try_See_User_Id = "room:try_see:";

	/** 直播间要添加僵尸粉的队列集合 ： 房间ID+_add_zombies_queue_map */
	public final static String add_zombies_queue_map = "room:add_zombies_queue_map:";

	/** 直播间僵尸粉用户信息key： 房间ID+zombies_list  */
	public final static String zombies_list = "room:zombies_user_list:";

	/** 一次直播的礼物金额记录 */ 
	public final static String live_StudioLog_gift = "live_StudioLog_gift:";
	
	


}
