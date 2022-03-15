package com.onelive.common.model.req.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 首充返现活动配置参数
 */
@Data
@ApiModel
public class FirstRechargeActivityConfigSaveReq extends ActivityConfigSaveReq {
    @ApiModelProperty(value = "返还充值金额比例[必填]",required = true)
    private BigDecimal returnPercent;

}
