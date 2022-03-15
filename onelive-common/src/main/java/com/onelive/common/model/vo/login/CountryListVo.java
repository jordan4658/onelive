package com.onelive.common.model.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 国家列表实体类
 */
@Data
@ApiModel
public class CountryListVo {
    @ApiModelProperty("国家名称")
    private String country;

    @ApiModelProperty("地区code值")
    private String areaCode;

    @ApiModelProperty(value = "国家code值")
    private String countryCode;}
