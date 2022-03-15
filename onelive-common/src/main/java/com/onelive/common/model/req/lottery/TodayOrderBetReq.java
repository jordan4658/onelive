package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "今日投注列表请求类")
public class TodayOrderBetReq {

    @ApiModelProperty(value = "彩票id [必填]")
    private Integer lotteryId;

}
