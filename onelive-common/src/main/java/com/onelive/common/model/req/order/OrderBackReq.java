package com.onelive.common.model.req.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单撤销请求参数")
public class OrderBackReq {
    @ApiModelProperty(value = "订单id[必填]",required = true)
    private Integer orderId;//订单id

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Integer userId;//订单id

}
