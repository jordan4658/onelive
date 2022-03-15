package com.onelive.common.model.dto.operate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 首充返现参数
 */
@Data
public class FirstRechargeActivityConfigDTO {
    @ApiModelProperty("返还充值金额比例")
    private BigDecimal returnPercent;
}
