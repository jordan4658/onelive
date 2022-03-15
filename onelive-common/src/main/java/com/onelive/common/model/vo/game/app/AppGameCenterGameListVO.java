package com.onelive.common.model.vo.game.app;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类游戏信息列表实体类
 */
@Data
@ApiModel
public class AppGameCenterGameListVO {
    @ApiModelProperty("主键ID")
    private Long id;
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

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }

}
