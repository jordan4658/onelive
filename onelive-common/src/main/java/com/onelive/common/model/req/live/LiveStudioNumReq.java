package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间Num")
public class LiveStudioNumReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("直播间Num  [必填]")
	private String studioNum;

}
