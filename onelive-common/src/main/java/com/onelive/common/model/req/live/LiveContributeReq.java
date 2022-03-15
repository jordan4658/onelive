package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "贡献榜请求参数类")
public class LiveContributeReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("房间号")
    private String studioNum;

    @ApiModelProperty("贡献榜类型 0日榜1周榜2月榜")
    private Integer type = 0;
}
