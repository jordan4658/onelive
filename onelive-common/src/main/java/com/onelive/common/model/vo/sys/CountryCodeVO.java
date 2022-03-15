package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("国家地区code信息实体类")
public class CountryCodeVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("国家名称")
    private String zhName;

    @ApiModelProperty("国家编号")
    private String countryCode;
}
