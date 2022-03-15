package com.onelive.common.model.req.live;

import java.io.Serializable;

import com.onelive.common.model.common.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LiveRecommendReq  extends PageReq implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("房间号[必填]")
    private String studioNum;

}
