package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OrderDetailsByOrderNoReq {

    @ApiModelProperty(value = "[必填]充值订单号",required = true)
    private String orderNo;
}
