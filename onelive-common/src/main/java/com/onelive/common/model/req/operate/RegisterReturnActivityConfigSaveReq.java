package com.onelive.common.model.req.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 注册返现活动配置参数
 */
@Data
@ApiModel
public class RegisterReturnActivityConfigSaveReq extends ActivityConfigSaveReq {

    @ApiModelProperty(value = "同IP限制[必填]",required = true)
    private Integer ipLimit;

    @ApiModelProperty(value = "同设备限制[必填]",required = true)
    private Integer deviceLimit;

    @ApiModelProperty(value = "打码倍率[必填]",required = true)
    private BigDecimal ratio;

    @ApiModelProperty(value = "金额[必填]",required = true)
    private BigDecimal amount;

}
