package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播开始
 */
@Data
@ApiModel
public class LiveBeginReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("直播间标题 [必填]")
	private String studioTitle;

	@ApiModelProperty("直播间封面图（大图）[必填]")
	private String studioBackground;
	@ApiModelProperty("直播间封面图（小图,暂无用，如有小图设置此值）")
	private String studioThumbImage;

	@ApiModelProperty("国家sys_country.countryCode")
	private String countryCode;

	@ApiModelProperty("默认0:绿播 1:黄播 [必填]")
	private Integer colour;

	@ApiModelProperty("画质 [必填]")
	private String sharpness;

	@ApiModelProperty("商户编码")
	private String merchantCode;

	@ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id）")
	private Integer productId;

	@ApiModelProperty("试看时长:超过时间后要付费")
	private Integer trySeeTime;

	@ApiModelProperty("游戏id")
	private Long gameId;

}