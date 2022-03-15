package com.onelive.common.model.vo.game.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏平台列表实体类")
public class GamePlatformVO {
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
     * 是否显示
     */
    @ApiModelProperty("是否显示")
    private Boolean isShow;
}
