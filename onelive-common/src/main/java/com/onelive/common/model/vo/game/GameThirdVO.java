package com.onelive.common.model.vo.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("第三方游戏信息实体类")
public class GameThirdVO {
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 游戏编号
     */
    @ApiModelProperty("游戏编号")
    private Long gameId;

    @ApiModelProperty("游戏平台代码")
    private String platformCode;

    /**
     * 支持地区
     */
    @ApiModelProperty("支持地区")
    private List<String> countryCodeList;

    /**
     * 游戏名称
     */
    @ApiModelProperty("游戏名称")
    private String name;

    /**
     * 游戏图标
     */
    @ApiModelProperty("游戏图标")
    private String icon;

    /**
     * 游戏分类id 关联game_category.category_id
     */
    @ApiModelProperty("游戏分类id")
    private Integer categoryId;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 真人游戏限红ID
     */
    @ApiModelProperty("真人游戏限红ID")
    private Integer oddType;

    /**
     * 是否开售 0否1是
     */
    @ApiModelProperty("是否开售")
    private Boolean isWork;


    public void setIcon(String icon) {
        this.icon = AWSS3Util.getAbsoluteUrl(icon);
    }
}
