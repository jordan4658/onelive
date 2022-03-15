package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩票玩法请求类")
public class LotteryPlayReq {
    @ApiModelProperty(value = "彩种分类id[必填]",required = true)
    private Integer cateId;
    @ApiModelProperty(value = "彩种id[必填]",required = true)
    private Integer lotteryId;
}
