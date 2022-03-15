package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class OnlineAmtAndOfflineAmtVO {

    @ApiModelProperty("订单类型：1-线上，2-线下")
    private Integer orderType;

    @ApiModelProperty("总充值金额")
    private BigDecimal amt;
}
