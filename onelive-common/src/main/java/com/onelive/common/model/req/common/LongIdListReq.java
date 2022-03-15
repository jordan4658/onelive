package com.onelive.common.model.req.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "ID数组参数")
public class LongIdListReq {
    @ApiModelProperty("Long类型ID数组[必填]")
    private List<Long> ids;
}
