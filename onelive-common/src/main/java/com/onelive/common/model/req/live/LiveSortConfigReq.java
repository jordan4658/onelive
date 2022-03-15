package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LiveSortConfigReq implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("推荐栏目直播间排序类型 1 在线人数，2礼物金额 ， 3随机排序， 4开播时间")
	private Integer sortCode;

}