package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩票详情求类")
public class CaiDetailReq {
    @ApiModelProperty(value = "订单id[必填]",required = true)
    private Integer orderId;

}
