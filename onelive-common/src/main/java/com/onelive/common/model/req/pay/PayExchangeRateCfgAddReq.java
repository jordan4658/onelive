package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 * 汇率配置
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Data
@ApiModel
public class PayExchangeRateCfgAddReq {

    @ApiModelProperty("国家代码")
    private String currencyCode;

    @ApiModelProperty("提现汇率浮动值(百分比)，在当前汇率上加一些（默认0）")
    private String txFloatingValue;

    @ApiModelProperty("充值汇率浮动值(百分比)，在当前汇率上减一些（默认0）")
    private String czFloatingValue;

}
