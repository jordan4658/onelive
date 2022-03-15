package com.onelive.common.constants.business;

/**
 * @Cacheable 用户端查询 用到的缓存key
 * @CacheEvict 后台更新数据时方法加此注解,删除缓存
 * 
 * @author mao
 *
 */
public class RedisCacheableKeys {

	/** 首页栏目 */
	public static final String LIVE_COLUMN = "live_column:";
	
	/** 礼物列表（全部商品列表）*/
	public static final String GIFT_LIST = "gift_list:";

	/** 直播礼物列表（只能在直播间展示的礼物列表）*/
	public static final String LIVE_GIFT_LIST = "live_gift_list:";

	/** 推荐区的四个直播间*/
	public static final String ROOM_RECOMMEND = "room_recommend:";
	
	/** 推荐区的四个直播间号*/
	public static final String ROOM_RECOMMEND_STUDIONUM = "room_recommend_studionum:";
	
	/** 弹幕商品*/
	public static final String GIFT_BARRAGE = "GIFT_BARRAGE:";
	
	

}
