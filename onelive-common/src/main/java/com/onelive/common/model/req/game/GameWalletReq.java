package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏钱包余额查询请求参数")
public class GameWalletReq {

    /**
     * 游戏分类钱包字段, 区分不同分类不同的钱包,
     OBG_ZR=1 真人游戏
     OBG_TY=2 体育游戏
     OBG_QP=3 棋牌游戏
     OBG_BY=4 捕鱼游戏
     OBG_LHJ=5 老虎机游戏
     */
    @ApiModelProperty("钱包类型")
    private Integer gameWallet;
}
