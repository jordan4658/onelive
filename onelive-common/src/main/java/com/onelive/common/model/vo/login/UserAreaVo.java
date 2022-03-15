package com.onelive.common.model.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 检测用户所在地区实体类
 */
@Data
@ApiModel
public class UserAreaVo {
    @ApiModelProperty(value = "地区code值")
    private String areaCode;

    @ApiModelProperty(value = "国家code值")
    private String countryCode;

    @ApiModelProperty(value = "国家名称")
    private String country;

    @ApiModelProperty(value = "国家列表")
    List<CountryListVo> list;
}
