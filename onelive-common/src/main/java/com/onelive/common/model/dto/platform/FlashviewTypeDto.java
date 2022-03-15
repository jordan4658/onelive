package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "轮播图类型")
public class FlashviewTypeDto implements Serializable {

    private static final long serialVersionUID=1L;
    
    @ApiModelProperty("类型值")
    private String typeCode;
    
    @ApiModelProperty("类型名字")
    private String typeName;

}
