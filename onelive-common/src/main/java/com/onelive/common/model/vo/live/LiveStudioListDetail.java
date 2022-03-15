package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播间表VO
 */
@Data
@ApiModel
public class LiveStudioListDetail {
	
	@ApiModelProperty("直播间ID")
	private Integer studioId;
	
	@ApiModelProperty("排序值,越小越前")
	private Integer sortNum;
	
	@ApiModelProperty("在线用户人数的倍数,默认0")
	private Integer memCountMultiple;
	
	@ApiModelProperty("直播音视频拉流地址")
	private String studioLivePath;
	
//	@ApiModelProperty("直播音频拉流地址")
//	private String studioLiveVoicePath;
//
//	@ApiModelProperty("直播推流地址")
//	private String studioLivePushFlow;

}