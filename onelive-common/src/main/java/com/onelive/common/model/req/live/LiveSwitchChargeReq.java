package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 主播开启/关闭直播间状态为收费
 */
@Data
@ApiModel(value = "主播开启/关闭直播间状态为收费")
public class LiveSwitchChargeReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id），空即免费")
	private Integer productId;
	
}