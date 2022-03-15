package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于选择的游戏信息类
 */
@Data
@ApiModel("用于选择的游戏实体类")
public class GameSelectGameVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("游戏名称")
    private String name;

    @ApiModelProperty("游戏唯一标识")
    private String gameCode;

}
