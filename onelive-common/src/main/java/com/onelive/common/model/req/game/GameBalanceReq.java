package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户余额查询请求参数
 */
@Data
@ApiModel("用户余额查询请求参数")
public class GameBalanceReq {
    @ApiModelProperty(value = "游戏分类ID, 关联game_category.category_id[必填]",required = true)
    private Integer categoryId;
}
