package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PayThreeProviderSelectVO {

    @ApiModelProperty("支付商id")
    private Long providerId;

    @ApiModelProperty("支付商名稱")
    private String providerName;
}
