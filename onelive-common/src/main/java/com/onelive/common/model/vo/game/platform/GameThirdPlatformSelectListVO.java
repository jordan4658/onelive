package com.onelive.common.model.vo.game.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方平台下拉选项列表实体类")
public class GameThirdPlatformSelectListVO {

    @ApiModelProperty("平台名称")
    private String name;

    @ApiModelProperty("平台代码")
    private String platformCode;

}
