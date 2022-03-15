package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
@ApiModel("公共配置信息")
public class PublicConfigVO {

    @ApiModelProperty("金币单位")
    private String goldUnit;

    @ApiModelProperty("银币单位")
    private String silverBeanUnit;

    @ApiModelProperty("客服域名")
    private String onlineServiceUrl;

    /**
     * 当前汇率（其他货币兑换美金的汇率）
     */
    @ApiModelProperty("当前汇率")
    private String exchange;

    /**
     * 提现汇率：比当前汇率高（默认为当前汇率）
     */
    @ApiModelProperty("提现汇率")
    private String txExchange;

    /**
     * 充值汇率：比当前汇率低（默认为当前汇率）
     */
    @ApiModelProperty("充值汇率")
    private String czExchange;

    @ApiModelProperty("当前国家code")
    private String countryCode;
}
