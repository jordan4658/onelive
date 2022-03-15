package com.onelive.common.model.vo.game.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏标签列表下拉内容实体类")
public class GameTagSelectListVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("类型名称")
    private String name;

    @ApiModelProperty("标签唯一标识")
    private String code;
}
