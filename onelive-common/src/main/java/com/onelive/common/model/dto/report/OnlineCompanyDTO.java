package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName OnlineCompanyDTO
 * @Desc 线下与线上入款dto
 * @Date 2021/4/23 10:15
 */
@Data
public class OnlineCompanyDTO {
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
    