package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏登陆请参数
 */
@Data
@ApiModel("第三方游戏-登陆请求参数")
public class GameLoginReq {
    @ApiModelProperty(value = "游戏code [必填]",required = true)
    private String gameCode;

}
