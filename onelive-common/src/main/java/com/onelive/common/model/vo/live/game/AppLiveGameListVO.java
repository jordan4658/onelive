package com.onelive.common.model.vo.live.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类游戏信息列表实体类
 */
@Data
@ApiModel("APP端直播间分类游戏信息列表实体类")
public class AppLiveGameListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String iconUrl;

    /**
     * 游戏类型8000=彩票, 其他关联game_category.category_id
     */
    @ApiModelProperty("游戏类型ID")
    private Integer categoryId;

    /**
     * 游戏ID, 关联lottery.lotteryId 或 game_third.game_id
     */
    @ApiModelProperty("游戏code")
    private String gameCode;

    /**
     * 是否第三方游戏 0否1是
     */
    @ApiModelProperty("是否第三方游戏")
    private Boolean isThird;
}
