package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用于选择的游戏信息类
 */
@Data
@ApiModel("用于选择的游戏信息类")
public class GameSelectListVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;
    /**
     * 分类ID
     */
    @ApiModelProperty("分类编号")
    private Integer categoryId;

    @ApiModelProperty("游戏列表")
    private List<GameSelectGameVO> gameList;

}
