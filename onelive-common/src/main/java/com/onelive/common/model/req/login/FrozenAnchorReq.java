package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "主播冻结解冻账号")
public class FrozenAnchorReq {

	@ApiModelProperty("主播的userid")
	private Long userId;
	
    @ApiModelProperty(value = "是否冻结 ", required = true)
    private Boolean isFrozen;
    
}
