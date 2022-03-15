package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类游戏信息列表实体类
 */
@Data
@ApiModel
public class GameCenterGameListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 游戏名称
     */
    @ApiModelProperty("游戏名称")
    private String name;
    /**
     * 游戏唯一标识
     */
    @ApiModelProperty("游戏唯一标识")
    private String gameCode;
    /**
     * 游戏运行状态：1：维护中，2：启用，3：停用
     */
    @ApiModelProperty("游戏运行状态(第三方状态)：1：维护中，2：启用，3：停用")
    private Integer status;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("本地状态:是否显示")
    private Boolean isShow;
}
