package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class WithdrawReqFeign {

    @ApiModelProperty(value = "[必填]提现金币数量",required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "[必填]提现银行卡id",required = true)
    private Long BankAccid;

    @ApiModelProperty(value = "用户ID",required = true)
    private Long userId;

    @ApiModelProperty(value = "提现用户IP",required = true)
    private String requestIp;

    @ApiModelProperty(value = "请求来源 ios、android、pc",required = true)
    private String source;

}
