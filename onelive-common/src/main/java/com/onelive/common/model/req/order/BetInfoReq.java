package com.onelive.common.model.req.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *根据订单ID查询下注信息,用于跟投
 */
@Data
@ApiModel("根据订单ID查询下注信息的请求参数")
public class BetInfoReq {
    @ApiModelProperty(value = "订单ID",required = true)
    private Integer orderId;
//    @ApiModelProperty(value = "彩票ID",required = true,example = "2302")
//    private Integer lotteryId;
}
