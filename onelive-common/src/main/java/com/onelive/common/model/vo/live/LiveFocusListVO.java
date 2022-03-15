package com.onelive.common.model.vo.live;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播间关注listVO
 */
@Data
@ApiModel
public class LiveFocusListVO {
	/**
	 * 直播间ID
	 */
	@ApiModelProperty("直播间ID")
	private Integer studioId;
	
	@ApiModelProperty("房间号")
	private String studioNum;
	
	@ApiModelProperty("是否推广 0:否 1:是")
	private Boolean isPromotion;
	
	@ApiModelProperty("当前所在城市名")
	private String cityName;
	
	@ApiModelProperty("用户ID")
	private Long userId;
	
	@ApiModelProperty("商户编码")
	private String merchantCode;
	
	@ApiModelProperty("收费类型:0:免费 1 :按时收费 2:按场收费 ")
	private Integer chargeType;
	
	@ApiModelProperty("游戏id")
	private Long gameId;
	
	@ApiModelProperty("试看时长:超过时间后要付费")
	private Integer trySeeTime;
	
	@ApiModelProperty("按时收费:每N分钟收费金额,按场收费:一场的收费金额")
	private BigDecimal chargeMoney;

	@ApiModelProperty("在线登录观看人数")
	private Integer viewsNumber;
	
	@ApiModelProperty("画质")
	private String sharpness;

	@ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
	private Integer studioStatus;

	@ApiModelProperty("国家sys_country.id")
	private Integer countryId;
	
	@ApiModelProperty("默认0:绿播 1:黄播")
	private Integer colour;

	@ApiModelProperty("首页栏目ID—关联— live_column.columnCode")
	private String columnCode;

	@ApiModelProperty("直播间封面图（大图）")
	private String studioBackground;
	
	@ApiModelProperty("直播间封面图（小图,暂无用，如有小图设置此值）")
	private String studioThumbImage;

	/**
	 * 直播间标题
	 */
	@ApiModelProperty("直播间标题")
	private String studioTitle;

	@ApiModelProperty("直播间火力值")
	private String studioHeat;

}