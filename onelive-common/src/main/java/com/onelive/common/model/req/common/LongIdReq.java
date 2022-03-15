package com.onelive.common.model.req.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ID参数")
public class LongIdReq {
    @ApiModelProperty("Long类型 ID[必填]")
    private Long id;
}
