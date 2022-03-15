package com.onelive.common.model.req.game.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更新标签状态请求参数")
public class GameTagUpdateStatusReq {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("是否显示")
    private Boolean isShow;

}
