package com.onelive.common.model.vo.live.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
@ApiModel("直播分类游戏实体类")
public class LiveGameVO {
    /**
     * 主键
     */
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("标签code")
    private String code;

    /**
     * 游戏分类id,关联game_category.category_id
     */
    @ApiModelProperty("游戏分类id")
    private Integer categoryId;

    /**
     * 游戏id, 关联game_third.id
     */
    @ApiModelProperty("游戏code")
    private String gameCode;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示")
    private Boolean isShow;

    @ApiModelProperty("多语言列表")
    private List<LiveGameLangVO> langList;

}
