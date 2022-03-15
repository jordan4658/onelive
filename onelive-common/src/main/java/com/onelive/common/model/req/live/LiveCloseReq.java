package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "关闭直播传输类")
public class LiveCloseReq implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("直播num")
	private String studioNum;

}
