package com.onelive.common.model.vo.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 游戏标签实体类
 */
@Data
@ApiModel("游戏标签实体类")
public class GameTagVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty(value = "国家code")
    private String countryCode;

    @ApiModelProperty(value = "图片地址")
    private String iconUrl;

    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示 0否1是")
    private Boolean isShow;

    @ApiModelProperty("多语言列表")
    private List<GameTagLangVO> langList;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }
}
