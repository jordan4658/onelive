package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间游戏列表请求类")
public class LotteryGameCodeReq{
    @ApiModelProperty(value = "标签code值[必填]",required = true)
    private String code;
}
