package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏列表同步参数")
public class GameListSyncReq {

    @ApiModelProperty("游戏分类ID")
    private Integer categoryId;

}
