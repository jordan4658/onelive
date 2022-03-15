package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播间表VO
 */
@Data
@ApiModel
public class LiveStudioListVo {
	/**
	 * 直播间ID
	 */
	@ApiModelProperty("直播间ID")
	private Integer studioId;
	
	@ApiModelProperty("房间号")
	private String studioNum;

	@ApiModelProperty("是否固定位置 1:是")
	private Boolean isFixed;
	
	@ApiModelProperty("是否推广 0:否 1:是")
	private Boolean isPromotion;
	
	@ApiModelProperty("当前所在城市名")
	private String cityName;
	
	@ApiModelProperty("排序值,越小越前")
	private Integer sortNum;

	@ApiModelProperty("用户ID")
	private Long userId;
	
	@ApiModelProperty("主播表id")
	private Integer anchorId;
	
	@ApiModelProperty("商户编码")
	private String merchantCode;
	
	
	@ApiModelProperty("游戏id")
	private Long gameId;
	
	@ApiModelProperty("试看时长:超过时间后要付费")
	private Integer trySeeTime;
	
	@ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id）")
	private Integer productId;
	
	/**
	 * 在线登录观看人数
	 */
	@ApiModelProperty("在线登录观看人数")
	private String viewsNumber;
	
	/**
	 * 画质
	 */
	@ApiModelProperty("画质")
	private String sharpness;

	/**
	 * 直播间状态 0：未开播，1：开播，2：网络状态不好
	 */
	@ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
	private Integer studioStatus;

	@ApiModelProperty("国家sys_country.countryCode")
	private String countryCode;
	
	/**
	 * 默认0:绿播 1:黄播
	 */
	@ApiModelProperty("默认0:绿播 1:黄播")
	private Integer colour;

	/**
	 * 首页栏目ID—关联— live_column.columnCode
	 */
	@ApiModelProperty("首页栏目ID—关联— live_column.columnCode")
	private String columnCode;

	/**
	 * 直播间类型—关联— live_studio_type.studio_type_id
	 */
	@ApiModelProperty("直播间类型—关联— live_studio_type.studio_type_id")
	private String studioTypeId;

	/**
	 * 直播间封面图
	 */
	@ApiModelProperty("直播间封面图（大图）")
	private String studioBackground;
	/**
	 * 直播间封面图
	 */
	@ApiModelProperty("直播间封面图（小图,暂无用，如有小图设置此值）")
	private String studioThumbImage;

	@ApiModelProperty("推荐排序字段默认 0:一般排序 -1:推荐优先 2:置底")
	private Integer isFirst;

	/**
	 * 直播间标题
	 */
	@ApiModelProperty("直播间标题")
	private String studioTitle;
	/**
	 * 直播间火力值
	 */
	@ApiModelProperty("直播间火力值")
	private String studioHeat;
}