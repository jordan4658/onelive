package com.onelive.common.model.dto.pay;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommonBalanceChangeDTO {

   //账变金币金额
    private BigDecimal change_amount;

    //账变银豆金额
    private BigDecimal change_silver_amount;

    //账变前金币余额
    private BigDecimal pre_amount;

    //账变前银豆余额
    private BigDecimal pre_silver_amount;

    //账变后银豆余额
    private BigDecimal later_silver_amount;

    //账变后金币余额
    private BigDecimal later_amount;

    //账变打码量
    private BigDecimal change_dml;

    //账变前打码量
    private BigDecimal pre_dml;

    //账变后打码量
    private BigDecimal later_dml;

    //累计打码量
    private BigDecimal sum_dml;

    //累计充值金额
    private BigDecimal sum_recharge_amount;

    //累计提现金额
    private BigDecimal sum_withdraw_amount;

    //最大充值金额
    private BigDecimal payMax;

    //首次充值金额
    private BigDecimal payFirst;

    //充值总次数
    private Integer pay_num;

    //最大提现金额
    private BigDecimal withdrawal_max;

    //首次提现金额
    private BigDecimal withdrawal_first;

    //提现总次数
    private Integer withdrawal_num;

}
