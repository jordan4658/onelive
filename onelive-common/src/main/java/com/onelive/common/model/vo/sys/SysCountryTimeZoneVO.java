package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("国家和时区")
public class SysCountryTimeZoneVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("时区")
    private String timeZone;

    @ApiModelProperty("国家")
    private String country;
}
