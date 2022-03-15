package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "守护榜请求参数类")
public class LiveGuardReq {

    @ApiModelProperty("房间号")
    private String studioNum;

    @ApiModelProperty("守护榜类型 0日榜1周榜2月榜")
    private Integer type = 0;

}
