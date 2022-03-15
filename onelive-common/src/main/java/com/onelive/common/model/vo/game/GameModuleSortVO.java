package com.onelive.common.model.vo.game;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 游戏排序
 * </p>
 */
@Data
@ApiModel
public class GameModuleSortVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("模块id")
    private Long moduleId;

    @ApiModelProperty("显示名称")
    private String showName;
     
    @ApiModelProperty("游戏名称")
    private String gameName;

    @ApiModelProperty("图片地址")
    private String imgUrl;

    @ApiModelProperty("状态 0显示1隐藏")
    private Boolean isHide;

    @ApiModelProperty("语言")
    private String lang;

  
}
