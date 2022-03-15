package com.onelive.common.model.req.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("第三方游戏信息保存参数")
public class GameThirdSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty(value = "游戏名称[必填]", required = true)
    private String name;

    @ApiModelProperty("平台代码 [必填]")
    private String platformCode;

    /**
     * 游戏编号
     */
    @ApiModelProperty(value = "游戏编号[必填]",required = true)
    private Long gameId;

    /**
     * 支持地区
     */
    @ApiModelProperty(value ="支持地区[必填]",required = true)
    private List<String> countryCodeList;

    /**
     * 游戏图标
     */
    @ApiModelProperty(value ="游戏图标[必填]",required = true)
    private String icon;

    /**
     * 游戏分类id 关联game_category.category_id
     */
    @ApiModelProperty(value ="游戏分类id,关联game_category.category_id[必填]",required = true)
    private Integer categoryId;

    /**
     * 排序
     */
    @ApiModelProperty(value ="排序[必填]",required = true)
    private Integer sort;

    /**
     * 真人游戏限红ID
     */
    @ApiModelProperty(value ="真人游戏限红ID")
    private Integer oddType;

    /**
     * 是否开售 0否1是
     */
    @ApiModelProperty(value ="是否开售[必填]",required = true)
    private Boolean isWork;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty(value ="是否显示[必填]",required = true)
    private Boolean isShow;

    public void setIcon(String icon) {
        this.icon = AWSS3Util.getRelativeUrl(icon);
    }
}
