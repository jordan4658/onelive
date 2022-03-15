package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class PayExchangeCurrencyAddReq {

    @ApiModelProperty("汇率国家代码")
    private String currencyCode;

    @ApiModelProperty("转换前的货币代码")
    private String currencyF;

    @ApiModelProperty("转换前的货币名称")
    private String currencyFName;

    @ApiModelProperty("转换成的货币代码")
    private String currencyT;

    @ApiModelProperty("转换成的货币名称")
    private String currencyTName;

    @ApiModelProperty("转换金额")
    private String currencyFD;

    @ApiModelProperty("要兑换的单位货币")
    private String currencyUnit;

    @ApiModelProperty("当前汇率（其他货币兑换美金的汇率）")
    private String exchange;

    @ApiModelProperty("查询时间,默认当前时间")
    private Date updateTime;

}
