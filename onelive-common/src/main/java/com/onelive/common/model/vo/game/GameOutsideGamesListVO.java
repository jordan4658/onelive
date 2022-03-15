package com.onelive.common.model.vo.game;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 第三方游戏
 * </p>
 */
@Data
@ApiModel
public class GameOutsideGamesListVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主键")
    private Long id;

     @ApiModelProperty("显示名称")
    private String showName;

     @ApiModelProperty("游戏分类")
    private String gameCategory;

     @ApiModelProperty("使用地区")
    private String countryName;

     @ApiModelProperty("状态 0启用1禁用")
    private Boolean isFrozen;

     @ApiModelProperty("更新时间")
    private Date updateTime;

     @ApiModelProperty("后台更新人账号")
    private String updateUser;


}
