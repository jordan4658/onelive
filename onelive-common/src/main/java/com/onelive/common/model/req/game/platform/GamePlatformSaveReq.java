package com.onelive.common.model.req.game.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏平台保存参数
 */
@Data
@ApiModel("第三方游戏平台保存参数")
public class GamePlatformSaveReq {
    @ApiModelProperty("主键ID,更新时传入")
    private Long id;

    /**
     * 第三方平台名称
     */
    @ApiModelProperty("第三方平台名称 [必填]")
    private String name;

    /**
     * 平台代码
     */
    @ApiModelProperty("平台代码 [必填]")
    private String platformCode;

    /**
     * 是否显示
     */
    @ApiModelProperty("是否显示 [必填]")
    private Boolean isShow;
}
