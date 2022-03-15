package com.onelive.common.model.dto.pay;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TotalUserIncomeAndExpensesDTO {

    /**
     * 统计日期
     */
    private String totalDate;

    /**
     * 金币收入
     */
    private BigDecimal goldIncome = new BigDecimal(0);

    /**
     * 金币支出
     */
    private BigDecimal goldExpend = new BigDecimal(0);

    /**
     * 银豆收入
     */
    private BigDecimal silverIncome = new BigDecimal(0);

    /**
     * 银豆支出
     */
    private BigDecimal silverExpend = new BigDecimal(0);

}
