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
public class GameOutsideGamesVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主键")
    private Long id;

     @ApiModelProperty("游戏显示名称")
    private String showName;

     @ApiModelProperty("游戏分类")
    private String gameCategory;

     @ApiModelProperty("logo图片地址")
    private String imgUrl;

     @ApiModelProperty("使用地区编码")
    private String countryCode;

     @ApiModelProperty("使用地区")
    private String countryName;

     @ApiModelProperty("状态 0启用1禁用")
    private Boolean isFrozen;

     @ApiModelProperty("更新时间")
    private Date updateTime;

     @ApiModelProperty("后台更新人账号")
    private String updateUser;


}
