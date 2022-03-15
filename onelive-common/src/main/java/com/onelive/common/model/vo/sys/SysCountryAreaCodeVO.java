package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 区号
 */
@Data
@ApiModel
public class SysCountryAreaCodeVO {

    @ApiModelProperty("区号")
    private String areaCode;

    @ApiModelProperty("国家")
    private String country;

}    
    