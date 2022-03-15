package com.onelive.common.model.vo.common;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("布尔类型实体类")
public class BooleanVO {

    @ApiModelProperty("布尔类型结果")
    private Boolean value;

}
