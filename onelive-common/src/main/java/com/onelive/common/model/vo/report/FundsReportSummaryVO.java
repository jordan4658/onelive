package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName FundsReportSummaryVO
 * @Desc 资金报表汇总
 * @Date 2021/4/22 14:45
 */
@Data
@ApiModel
public class FundsReportSummaryVO {

    @ApiModelProperty("充值总额")
    private BigDecimal totalRecharge = BigDecimal.ZERO;

    @ApiModelProperty("提现总额")
    private BigDecimal totalWithdraw = BigDecimal.ZERO;

    @ApiModelProperty("游戏总盈利")
    private BigDecimal totalGameProfit = BigDecimal.ZERO;

    @ApiModelProperty("总盈利")
    private BigDecimal totalProfit = BigDecimal.ZERO;
}
    