package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 彩票详情请求参数
 */
@Data
@ApiModel("彩票详情请求参数")
public class LotteryDetailReq {
    @ApiModelProperty(value = "订单id[必填]",required = true)
    private Integer orderId;//订单id
}
