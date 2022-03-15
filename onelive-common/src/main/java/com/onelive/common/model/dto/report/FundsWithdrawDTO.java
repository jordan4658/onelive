package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName FundsWithdrawDTO
 * @Desc 资金报表提现信息dto
 * @Date 2021/4/23 10:15
 */
@Data
public class FundsWithdrawDTO {

    /**
     * 报表日期
     */
    private Date reportDate;

    /**
     * 总提现金额
     */
    private BigDecimal sumWithdraw;


}
    