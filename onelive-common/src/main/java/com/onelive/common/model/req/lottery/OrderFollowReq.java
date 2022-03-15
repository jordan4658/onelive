package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "订单跟单请求类")
public class OrderFollowReq {
    @ApiModelProperty(value = "跟投的订单id列表[必填]")
    private List<Integer> orders;
    @ApiModelProperty(value = "用户id",hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "房间id[必填]")
    private String studioNum;
    @ApiModelProperty(value = "来源",hidden = true)
    private String source;

}
