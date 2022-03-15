package com.onelive.common.model.vo.pay;

import lombok.Data;

import java.util.Date;

/**
 * 查询汇率实体VO
 */
@Data
public class ExchangeCurrencyVO {
    /**
     * 转换汇率前的货币代码
     */
    private String currencyF;

    /**
     * 转换汇率前的货币名称
     */
    private String currencyF_Name;

    /**
     * 转换汇率成的货币代码
     */
    private String currencyT;

    /**
     * 转换汇率成的货币名称
     */
    private String currencyT_Name;

    /**
     * 转换前货币金额
     */
    private String currencyFD;

    /**
     * 当前汇率
     */
    private String exchange;

    /**
     * 当前汇率
     */
    private String result;

    /**
     * 查询日期
     */
    private Date updateTime;

}
