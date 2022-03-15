package com.onelive.common.model.req.game.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏信息 请求参数")
public class GameInfoSelectReq {

    @ApiModelProperty(value = "分类编号 [必填]")
    private Integer categoryId;

}
