package com.onelive.common.model.vo.game;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 游戏模块表
 * </p>
 */
@Data
@ApiModel
public class GameModuleListVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("类型id")
    private Long typeId;

     @ApiModelProperty("模块名称")
    private String moduleName;

     @ApiModelProperty("状态 0显示1隐藏")
    private Boolean isHide;

     @ApiModelProperty("显示数量")
    private Integer showNum;
     
     @ApiModelProperty("游戏数量")
    private Integer gameNum;


}
