package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName CountryVO
 * @Desc 国家信息展示类
 * @Date 2021/4/5 19:14
 */
@Data
@ApiModel
public class CountryVO {

    @ApiModelProperty("国家名称，根据header的lang，会显示不同的语言名称")
    private String name;

    @ApiModelProperty("国家编号")
    private String countryCode;

    @ApiModelProperty("手机区号")
    private String areaCode;

    @ApiModelProperty("所属国家的语言")
    private String lang;

}    
    