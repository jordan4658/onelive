package com.onelive.common.model.vo.live.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类游戏信息列表实体类
 */
@Data
@ApiModel("直播间分类游戏信息列表实体类")
public class LiveGameListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 游戏名称
     */
    @ApiModelProperty("游戏名称")
    private String name;
    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示")
    private Boolean isShow;
}
