package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间收费商品")
public class ProductChargeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("礼物id")
	private Integer giftId;

	@ApiModelProperty("礼物平台币价格")
	private BigDecimal price;

}
