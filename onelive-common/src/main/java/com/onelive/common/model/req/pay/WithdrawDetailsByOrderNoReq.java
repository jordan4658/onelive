package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WithdrawDetailsByOrderNoReq {

    @ApiModelProperty(value = "[必填]提现订单号", required = true)
    private String orderNo;
}
