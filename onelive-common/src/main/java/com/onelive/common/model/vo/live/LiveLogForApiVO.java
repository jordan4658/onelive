package com.onelive.common.model.vo.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播时间传输类")
public class LiveLogForApiVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID")
	private Long userId;
	
	@ApiModelProperty("直播时间，单位秒")
	private Long liveTime;
	
}