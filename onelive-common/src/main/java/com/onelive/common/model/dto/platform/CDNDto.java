package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "cdn")
public class CDNDto implements Serializable {

    private static final long serialVersionUID=1L;
    
    @ApiModelProperty("CDN 的 code")
    private String code;
    
    @ApiModelProperty("cdn名字")
    private String name;

}
