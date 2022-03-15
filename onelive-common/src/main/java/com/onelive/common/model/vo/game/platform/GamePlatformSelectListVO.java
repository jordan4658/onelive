package com.onelive.common.model.vo.game.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏平台选择列表实体类")
public class GamePlatformSelectListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 第三方平台名称
     */
    @ApiModelProperty("第三方平台名称")
    private String name;

    @ApiModelProperty("平台代码")
    private String platformCode;

}
