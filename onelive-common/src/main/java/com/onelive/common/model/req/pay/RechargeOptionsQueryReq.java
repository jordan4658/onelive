package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class RechargeOptionsQueryReq {


    @ApiModelProperty(value = "[必填]支付方式ID",required = true)
    private Long  payWayId;
}
