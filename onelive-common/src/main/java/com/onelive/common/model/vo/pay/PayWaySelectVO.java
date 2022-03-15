package com.onelive.common.model.vo.pay;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class PayWaySelectVO {

    @ApiModelProperty("支付方式主键id")
    private Long payWayId;

    @ApiModelProperty("支付方式的名称")
    private String payWayName;

    @ApiModelProperty("单笔最低充值金额")
    private BigDecimal minAmt;

    @ApiModelProperty("单笔最高充值金额")
    private BigDecimal maxAmt;


}
