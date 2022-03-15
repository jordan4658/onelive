package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏标签列表实体类")
public class GameTagListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String name;

    @ApiModelProperty("标签唯一标识")
    private String code;
    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示 false否 true是")
    private Boolean isShow;
}
