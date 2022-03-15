package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemUserFocusUserReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关注人id[必填]",required = true)
	private Long focusUserId;
	
	@ApiModelProperty(value = "是否在房间内关注,房间内进行广播[必填]",required = true)
	private boolean isInRoom;
	
	
}
