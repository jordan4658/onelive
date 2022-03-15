package com.onelive.common.model.vo.game.gamecenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏选择列表实体类")
public class GameThirdSelectListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 游戏名称
     */
    @ApiModelProperty("游戏名称")
    private String name;

    /**
     * 唯一标识
     */
    @ApiModelProperty("唯一标识")
    private String gameCode;

}
