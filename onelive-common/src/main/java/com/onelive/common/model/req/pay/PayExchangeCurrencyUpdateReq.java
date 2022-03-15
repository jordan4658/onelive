package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class PayExchangeCurrencyUpdateReq  extends  PayExchangeCurrencyAddReq{

    @ApiModelProperty("汇率ID")
    private Long exchangeCurrencyId;


}
