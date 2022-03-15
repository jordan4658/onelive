package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种列表查询请求类")
public class LotteryGameNoReq  {
    @ApiModelProperty(value = "游戏编码[必填]",required = true)
    private String gameNo;
}
