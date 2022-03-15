package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏上下分请参数
 */
@Data
@ApiModel("第三方游戏-上下分请求参数")
public class GameTransferReq {
    @ApiModelProperty(value = "游戏钱包类型,2真人游戏 3体育游戏 4棋牌游戏 5捕鱼游戏 6老虎机游戏  [必填]",required = true)
    private Integer walletType;

    @ApiModelProperty(value = "上下分金额[必填]",required = true)
    private String amount;

}
