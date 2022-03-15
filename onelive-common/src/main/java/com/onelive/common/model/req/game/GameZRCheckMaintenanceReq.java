package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 真人游戏维护状态查询参数
 */
@Data
@ApiModel("第三方游戏-真人游戏维护状态查询参数")
public class GameZRCheckMaintenanceReq {
    @ApiModelProperty(value = "游戏分类ID, 关联game_category.category_id[必填]",required = true)
    private Integer categoryId;
}
