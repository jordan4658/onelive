package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播分类游戏状态更新请求参数")
public class LiveGameStatusUpdateReq {
    @ApiModelProperty("主键ID[必填]")
    private Long id;

    @ApiModelProperty(value ="是否显示[必填]",required = true)
    private Boolean isShow;
}
