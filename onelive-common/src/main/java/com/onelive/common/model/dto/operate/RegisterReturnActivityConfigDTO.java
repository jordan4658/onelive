package com.onelive.common.model.dto.operate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 注册返现活动配置实体类
 */
@Data
public class RegisterReturnActivityConfigDTO {
    @ApiModelProperty("同IP限制")
    private Integer ipLimit;

    @ApiModelProperty("同设备限制")
    private Integer deviceLimit;

    @ApiModelProperty("打码倍率")
    private BigDecimal ratio;

    @ApiModelProperty("金额")
    private BigDecimal amount;
}
