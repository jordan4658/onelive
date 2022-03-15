package com.onelive.common.model.req.game.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏平台状态更新参数")
public class GamePlatformUpdateStatusReq {

    @ApiModelProperty("主键ID [必填]")
    private Long id;

    @ApiModelProperty("是否显示 [必填]")
    private Boolean isShow;
}
