package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游客切换地区")
public class AppVistorChangeAreaReq {

    @ApiModelProperty(value = "切换的国家code值")
    private String countryCode;

}
