package com.onelive.common.model.req.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物校验传输类")
public class GiftgCheckDto {

	@ApiModelProperty("礼物id[必填]")
	private Integer giftId;

}
