package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理/主播禁言观众传输类")
public class BannedReq {

	@ApiModelProperty("被禁言人id  [必填]")
	private Long bUserId;

	@ApiModelProperty("直播间Num [必填]")
	private String studioNum;
	
	@ApiModelProperty("禁言时长（秒） [必填]")
	private Integer bannedTime;
}
