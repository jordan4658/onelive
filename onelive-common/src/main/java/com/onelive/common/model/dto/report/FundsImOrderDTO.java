package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName FundsRechargeDTO
 * @Desc 资金报表IM体育注单信息dto
 * @Date 2021/4/23 10:15
 */
@Data
public class FundsImOrderDTO {

    /**
     * 报表日期
     */
    private Date reportDate;

    /**
     * 总下注金额
     */
    private BigDecimal totalComsumer;

    /**
     * 中奖金额
     */
    private BigDecimal playWinAmount;

}    
    