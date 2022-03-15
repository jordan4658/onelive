package com.onelive.common.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lorenzo
 * @Description 报表统计DTO(注单相关)
 * @Date 2021/5/26 10:03
 */
@Data
public class StatSourceBetDTO {
    // ios or android
    private String source;

    // 总条目数
    private Integer count;

    // 输赢
    private BigDecimal winLoss;

    // 总额
    private BigDecimal total;
}
