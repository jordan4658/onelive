package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class PayFindExchangeCfgUpdateReq extends PayFindExchangeCfgAddReq {

    @ApiModelProperty("汇率查询key ID")
    private Long exchangeKeyId;

//    @ApiModelProperty("查询汇率url")
//    private String exchangeUrl;



}
