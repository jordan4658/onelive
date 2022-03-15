package com.onelive.common.model.req.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 游戏标签保存请求参数
 */
@Data
@ApiModel("游戏标签保存请求参数")
public class GameTagSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;
    /**
     * 国家code 关联sys_country.country_code 例如: zh_CN
     */
    @ApiModelProperty(value = "国家code[必填]",required = true)
    private String countryCode;
    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址[必填]",required = true)
    private String iconUrl;
    /**
     * 排序
     */
//    @ApiModelProperty(value = "排序[必填]",required = true)
//    private Integer sort;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty(value = "是否显示 0否1是[必填]",required = true)
    private Boolean isShow;


    @ApiModelProperty(value = "标签多语言列表[必填]",required = true)
    private List<GameTagLangSaveReq> langList;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getRelativeUrl(iconUrl);
    }
}
