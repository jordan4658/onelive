package com.onelive.common.model.dto.operate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 推广返现活动配置实体类
 */
@Data
public class PopularizeActivityConfigDTO {
    @ApiModelProperty("同IP限制")
    private Integer ipLimit;

    @ApiModelProperty("同设备限制")
    private Integer deviceLimit;

    @ApiModelProperty("额外有效条件小时内")
    private BigDecimal hourCondition;

    @ApiModelProperty("有效点击")
    private Integer validHits;

    @ApiModelProperty("打码倍率")
    private BigDecimal ratio;

    @ApiModelProperty("金额")
    private BigDecimal amount;
}
