package com.onelive.common.model.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理/主播踢人传输类")
public class KickingDto {

	@ApiModelProperty("被踢人id[必填]")
	private Long bUserId;

	@ApiModelProperty("直播间Num [必填]")
	private String studioNum;
}
