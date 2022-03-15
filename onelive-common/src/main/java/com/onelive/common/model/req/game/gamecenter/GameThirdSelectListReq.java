package com.onelive.common.model.req.game.gamecenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏下拉选择请求参数")
public class GameThirdSelectListReq {

    @ApiModelProperty("分类编号")
    private Integer categoryId;

}
