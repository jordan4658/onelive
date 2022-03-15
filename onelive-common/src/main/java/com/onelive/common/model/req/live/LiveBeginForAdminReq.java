package com.onelive.common.model.req.live;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 直播开始
 */
@Data
@ApiModel
public class LiveBeginForAdminReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("直播间标题")
	private String studioTitle;

	@ApiModelProperty("直播间封面图（大图）")
	private String studioBackground;
	
	@ApiModelProperty("国家sys_country.country_code")
	private String countryCode;

	@ApiModelProperty("商户编码")
	private String merchantCode;

	@ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id）")
	private Integer productId;
	
	@ApiModelProperty("游戏id")
	private Long gameId;
	
	@ApiModelProperty("视频地址")
	private String videoUrl;
	
	@ApiModelProperty("下播时间")
	private String endTime;


	public void setStudioBackground(String studioBackground) {
		this.studioBackground = AWSS3Util.getRelativeUrl(studioBackground);
	}
}