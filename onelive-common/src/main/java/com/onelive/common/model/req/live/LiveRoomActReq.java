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
public class LiveRoomActReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("直播间活动类型 1游戏活动 2直播活动 不传值默认返回全部")
	private Integer type;

}