package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间悬浮窗类型")
public class FloatTypeDto implements Serializable {

    private static final long serialVersionUID=1L;
    
    @ApiModelProperty("类型值")
    private String floatType;
    
    @ApiModelProperty("类型名字")
    private String typeName;

}
