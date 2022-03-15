package com.onelive.common.model.dto.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RechargerReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/8 18:41
 */
@Data
@ApiModel
public class RechargeDTO {

    @ApiModelProperty("支付渠道id")
    private Long payWayId;

    @ApiModelProperty("充值金币数量")
    private BigDecimal price;

    @ApiModelProperty("国家编码code")
    private String currencyCode;

    @ApiModelProperty("实际充值对应的金额")
    private BigDecimal actualPayment;

    @ApiModelProperty("充值汇率")
    private String czExchange;

    @ApiModelProperty("入款姓名")
    private String payName;

    @ApiModelProperty("入款备注")
    private String payNot;

    @ApiModelProperty("来源系统：ios、android、pc")
    private String source;

    @ApiModelProperty("请求ip")
    private String requestIp;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("充值订单号")
    private String orderNo;

    @ApiModelProperty("支付渠道对应的银行标识")
    private String backCode;





}
