package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏-上下分状态查询请参数
 */
@Data
@ApiModel("第三方游戏-上下分状态查询请求参数")
public class GameTransferStatusReq {
    @ApiModelProperty(value = "游戏分类ID, 关联game_category.category_id[必填]",required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "交易单号[必填]",required = true)
    private String transferNo;
}
