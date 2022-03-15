package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class PayExchangeRateCfgUpdateReq extends PayExchangeRateCfgAddReq {

    @ApiModelProperty("汇率配置ID")
    private Long exchangeRateCfgId;


}
