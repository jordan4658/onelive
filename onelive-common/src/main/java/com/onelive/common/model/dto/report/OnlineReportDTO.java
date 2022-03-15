package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName OnlineReportDTO
 * @Desc 线上支付报表dto
 * @Date 2021/4/23 10:15
 */
@Data
public class OnlineReportDTO {

    /**
     * 支付设定名称
     */
    private String payTypeName;

    /**
     * 支付商名称
     */
    private String providerName;
    /**
     * 支付方式
     */
    private String payWayName;

    /**
     * 成功入账总金额
     */
    private BigDecimal successMoney;
    /**
     * 成功付款总人数
     */
    private Integer successPeople;
    /**
     * 成功次数
     */
    private Integer successCount;
    /**
     * 失败次数
     */
    private Integer failCount;

}    
    