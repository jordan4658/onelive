package com.onelive.common.model.vo.game;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 游戏类型表
 * </p>
 */
@Data
@ApiModel
public class GameTypeListVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("类型名称")
    private String typeName;

     @ApiModelProperty("状态 0显示1隐藏")
    private Boolean isHide;

     @ApiModelProperty("语言")
    private String lang;
}
