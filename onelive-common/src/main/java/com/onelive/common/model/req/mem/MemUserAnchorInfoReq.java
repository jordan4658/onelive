package com.onelive.common.model.req.mem;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemUserAnchorInfoReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "直播间num[必填]",required = true)
	private String studioNum;
}
