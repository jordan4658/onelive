package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游客登录信息")
public class AppVisitorLoginReq {

    @ApiModelProperty(value = "国家地区编号")
    private String countryCode;
}
