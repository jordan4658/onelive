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
public class LiveRoomDetailReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("房间号[必填]")
	private String studioNum;

}