package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间悬浮窗")
public class LiveFloatForIndexDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("展示类型(位置) card:主播名片  game:游戏轮播")
    private String showType;
    
    @ApiModelProperty("轮播名称")
    private String floatName;
    
    @ApiModelProperty("图片地址")
    private String imgUrl;

    @ApiModelProperty("适用国家id,多个逗号分隔,空即:所有")
    private String useCountry;

    @ApiModelProperty("跳转模块")
    private String skipModel;

    @ApiModelProperty("跳转路径")
    private String skipUrl;
    
    @ApiModelProperty("游戏分类id")
    private Integer categoryId;
    
    @ApiModelProperty("游戏id")
    private Integer lotteryId;

}
