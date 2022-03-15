package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间收费商品类型")
public class ProductTypeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("6：按时收费商品 7:按场收费商品 [必填]")
	private Integer type;


}
