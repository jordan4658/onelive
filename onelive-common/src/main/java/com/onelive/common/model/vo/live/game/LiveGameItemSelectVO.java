package com.onelive.common.model.vo.live.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播分类游戏选项游戏内容")
public class LiveGameItemSelectVO {
    @ApiModelProperty("游戏分类ID")
    private Integer categoryId;

    @ApiModelProperty("游戏code")
    private String gameCode;

    @ApiModelProperty("是否第三方游戏")
    private Boolean isThird;

    @ApiModelProperty("游戏名称")
    private String name;
}
