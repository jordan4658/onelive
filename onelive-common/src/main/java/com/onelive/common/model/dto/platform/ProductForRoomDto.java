package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "房间商品传输类")
public class ProductForRoomDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("门票购买时间")
	private Long buyTime;
	
	@ApiModelProperty("门票结束时间")
	private Long endTime;

	@ApiModelProperty("是否可用 -1:免费 0:不可以用(过期) 1:可用")
	private int isUsable;

}
