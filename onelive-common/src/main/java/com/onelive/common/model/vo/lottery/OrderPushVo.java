package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel(value = "订单推单返回类")
public class OrderPushVo {
    @ApiModelProperty(value = "彩票名称")
    public String lotteryName;
    @ApiModelProperty(value = "期号")
    public String issue;
    @ApiModelProperty(value = "订单号")
    public String orderSn;
    @ApiModelProperty(value = "昵称")
    public String nickName;
    @ApiModelProperty(value = "下注金额")
    public BigDecimal betAmount;
}
