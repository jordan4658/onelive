package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class MemGroupCurrencyCfgAddReq {

    @ApiModelProperty("国家")
    private String currencyCode;

    @ApiModelProperty("最大存款金额")
    private BigDecimal maxDeposit;

    @ApiModelProperty("入款总额")
    private BigDecimal totalDeposit;

    @ApiModelProperty("出款总额")
    private BigDecimal totalDispensing;
}
