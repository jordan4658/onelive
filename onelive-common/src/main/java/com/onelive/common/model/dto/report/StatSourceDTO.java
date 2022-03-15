package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lorenzo
 * @Description 报表统计DTO
 * @Date 2021/5/25 12:49
 */
@Data
public class StatSourceDTO {
    // ios or android
    private String source;

    // 总数
    private BigDecimal total;
}
