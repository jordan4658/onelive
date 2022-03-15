package com.onelive.common.model.vo.game.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏选择列表")
public class GameInfoSelectVO {
    /**
     * 游戏编号
     */
    @ApiModelProperty("游戏编号")
    private Long gameId;

    /**
     * 游戏名称
     */
    @ApiModelProperty("游戏名称")
    private String name;

}
