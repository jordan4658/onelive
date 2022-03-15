package com.onelive.common.model.vo.mem;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AnchorForAccVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("用户账号")
	private String userAccount;
	
//	@ApiModelProperty("直播时长(每次直播结束时统计)")
//	private Integer liveTime;

//	@ApiModelProperty("直播次数/登录次数")
//	private Integer liveCount;
	
	@ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
	private Integer studioStatus;

}
