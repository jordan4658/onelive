package com.onelive.common.model.vo.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName FundsReportListVO
 * @Desc 资金报表列表
 * @Date 2021/4/22 14:45
 */
@Data
@ApiModel
public class FundsReportListVO {

    @ApiModelProperty("查询日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportDate;

    @ApiModelProperty("充值总额")
    private BigDecimal totalRecharge;

    @ApiModelProperty("提现总额")
    private BigDecimal totalWithdraw;

    @ApiModelProperty("游戏总盈利")
    private BigDecimal totalGameProfit;

    @ApiModelProperty("总盈利")
    private BigDecimal totalProfit;

    @ApiModelProperty("首充人数")
    private Integer firstRechargeNum;

    @ApiModelProperty("游戏总消费")
    private BigDecimal gameTotalComsumer;

    @ApiModelProperty("玩家赢钱")
    private BigDecimal playWinAmount;

}
    