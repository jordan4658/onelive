package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName FundsRechargeDTO
 * @Desc 资金报表充值信息dto
 * @Date 2021/4/23 10:15
 */
@Data
public class FundsRechargeDTO {

    /**
     * 报表日期
     */
    private Date reportDate;

    /**
     * 总充值金额
     */
    private BigDecimal sumRecharge;

    /**
     * 首充人数
     */
    private Integer sumFirstNum;

}    
    