package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("根据TAG code 查询第三方游戏列表")
public class GameListByTagReq {
    @ApiModelProperty(value = "标签code[必填]", required = true)
    private String code;

    @ApiModelProperty(value = "语言", hidden = true)
    private String lang;
}
