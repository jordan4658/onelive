package com.onelive.common.model.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "弹幕发送传输类")
public class SendBarrageDto {

	@ApiModelProperty("主播ID [必填]")
	private Long hostId;

	@ApiModelProperty("弹幕内容 [必填]")
	String barrageContent;
}
