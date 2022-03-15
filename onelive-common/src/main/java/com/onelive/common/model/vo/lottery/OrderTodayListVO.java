package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderTodayListVO {
    @ApiModelProperty(value = "当日盈亏")
    private String todayEarnAmount;
    @ApiModelProperty(value = "当日中奖金额")
    private String todayWinAmount;
    @ApiModelProperty(value = "当日已结算投注金额")
    private String todayHasSettle;
    @ApiModelProperty(value = "当日未结投注金额")
    private String todayNoSettle;
}
