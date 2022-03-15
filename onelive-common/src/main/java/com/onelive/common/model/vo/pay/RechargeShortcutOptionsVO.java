package com.onelive.common.model.vo.pay;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class RechargeShortcutOptionsVO {

    @ApiModelProperty("充值快捷选项ID")
    private Long shortcutOptionsId;

    @ApiModelProperty("支付方式ID")
    private Long payWayId;


    @ApiModelProperty("充值金币数量")
    private String option;

    @ApiModelProperty("实际支付的币种金额")
    private BigDecimal price;

    @ApiModelProperty("金币选项单位，多语言")
    private String shortcutOptionsUnit;

    @ApiModelProperty("汇率")
    private String exChange;

    @ApiModelProperty("实际支付币种单位")
    private String currencyUint;



}
