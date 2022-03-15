package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "id传输通用类")
public class IdReq {

	@ApiModelProperty("主键[必填]")
	private Long id;


}
