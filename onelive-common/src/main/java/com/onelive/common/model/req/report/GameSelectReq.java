package com.onelive.common.model.req.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏下拉列表请求参数")
public class GameSelectReq {
    @ApiModelProperty("游戏平台code")
    private String code;
}
