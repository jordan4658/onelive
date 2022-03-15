package com.onelive.common.model.vo.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
@ApiModel("分类游戏实体类")
public class GameCenterGameVO {
    /**
     * 主键
     */
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 游戏平台代码, 关联game_third_platform.platform_code
     */
    @ApiModelProperty("游戏平台代码")
    private String platformCode;

    /**
     * 游戏名称
     */
    @ApiModelProperty("游戏名称")
    private String name;

    /**
     * 标签ID, 关联game_tag.id
     */
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
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String iconUrl;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示")
    private Boolean isShow;

    @ApiModelProperty("多语言列表")
    private List<GameCenterGameLangVO> langList;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }
}
