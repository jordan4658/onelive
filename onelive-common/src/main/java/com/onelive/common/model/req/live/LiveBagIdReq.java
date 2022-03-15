package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "背包ID参数")
public class LiveBagIdReq {
    @ApiModelProperty(value = "背包ID[必填]",required = true)
    private Long id;
}
