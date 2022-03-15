package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName HandleDTO
 * @Desc 手动入款dto
 * @Date 2021/4/23 10:15
 */
@Data
public class HandleDTO {
    /**
     * 总金额
     */
    private BigDecimal totalMoney;

    /**
     * 总人数
     */
    private Integer totalPeople;

}    
    