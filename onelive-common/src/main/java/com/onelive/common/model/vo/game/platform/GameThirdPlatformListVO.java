package com.onelive.common.model.vo.game.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 */
@Data
@ApiModel("第三方游戏平台列表实体类")
public class GameThirdPlatformListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 第三方平台名称
     */
    @ApiModelProperty("第三方平台名称")
    private String name;

    /**
     * 平台代码
     */
    @ApiModelProperty("平台代码")
    private String platformCode;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示")
    private Boolean isShow;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
}
