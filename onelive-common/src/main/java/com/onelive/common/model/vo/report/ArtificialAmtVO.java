package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class ArtificialAmtVO {

    @ApiModelProperty("处理类型：10-减款、11-加款")
    private Integer handleType;

    @ApiModelProperty("总金额")
    private BigDecimal artificialAmt;
}
