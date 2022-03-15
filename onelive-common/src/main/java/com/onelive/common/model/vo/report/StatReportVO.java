package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lorenzo
 * @Description 统计-报表统计VO
 * @Date 2021/5/25 13:23
 */
@Data
@ApiModel
public class StatReportVO {

    @ApiModelProperty("有效注单-总注单")
    private Integer betCount = 0;

    @ApiModelProperty("有效注单-IOS注单")
    private Integer iosBetCount = 0;

    @ApiModelProperty("有效注单-安卓注单")
    private Integer androidBetCount = 0;

    @ApiModelProperty("有效投注额-总投注")
    private BigDecimal betTotal = BigDecimal.ZERO;

    @ApiModelProperty("有效投注额-IOS投注")
    private BigDecimal iosBetTotal = BigDecimal.ZERO;

    @ApiModelProperty("有效投注额-安卓投注")
    private BigDecimal androidBetTotal = BigDecimal.ZERO;

    @ApiModelProperty("公司盈亏-总盈亏")
    private BigDecimal winLoss = BigDecimal.ZERO;

    @ApiModelProperty("公司盈亏-IOS盈亏")
    private BigDecimal iosWinLoss = BigDecimal.ZERO;

    @ApiModelProperty("公司盈亏-安卓盈亏")
    private BigDecimal androidWinLoss = BigDecimal.ZERO;

    @ApiModelProperty("公司充值-总充值")
    private BigDecimal rechargeTotal = BigDecimal.ZERO;

    @ApiModelProperty("公司充值-IOS充值")
    private BigDecimal iosRechargeTotal = BigDecimal.ZERO;

    @ApiModelProperty("公司充值-安卓充值")
    private BigDecimal androidRechargeTotal = BigDecimal.ZERO;

    @ApiModelProperty("会员提现-总提现")
    private BigDecimal withdrawTotal = BigDecimal.ZERO;

    @ApiModelProperty("会员提现-IOS提现")
    private BigDecimal iosWithdrawTotal = BigDecimal.ZERO;

    @ApiModelProperty("会员提现-安卓提现")
    private BigDecimal androidWithdrawTotal = BigDecimal.ZERO;

    @ApiModelProperty("会员注册-总注册")
    private Integer registeredCount = 0;

    @ApiModelProperty("会员注册-充值人数")
    private Integer rechargeCount = 0;

    @ApiModelProperty("会员注册-首次充值金额")
    private BigDecimal firstRechargeTotal = BigDecimal.ZERO;

}
