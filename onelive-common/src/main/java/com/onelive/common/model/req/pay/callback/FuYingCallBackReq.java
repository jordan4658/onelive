package com.onelive.common.model.req.pay.callback;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName FuYingCallBackReq
 * @Description TODO
 * @Author wk
 * @Date 2021/6/3 15:18
 **/
@Data
@ApiModel
public class FuYingCallBackReq {

    @ApiModelProperty(value = "支付订单号",required = true)
    private String payOrderId;

    @ApiModelProperty(value = "商户ID",required = true)
    private String mchId;

    @ApiModelProperty(value = "应用ID",required = true)
    private String appId;

    @ApiModelProperty(value = "支付产品ID",required = true)
    private Integer productId;

    @ApiModelProperty(value = "商户订单号（支付平台定义的商户指我们）",required = true)
    private String mchOrderNo;

    @ApiModelProperty(value = "支付金额",required = true)
    private Integer amount;

    @ApiModelProperty(value = "入账金额",required = true)
    private Integer income;

    @ApiModelProperty(value = "状态",required = true)
    private Integer status;

    @ApiModelProperty("渠道订单号")
    private String channelOrderNo;

    @ApiModelProperty("渠道数据包")
    private String channelAttach;

    @ApiModelProperty("支持终端")
    private String param1;

    @ApiModelProperty("扩展参数2")
    private String param2;

    @ApiModelProperty(value = "支付成功时间",required = true)
    private String paySuccTime;

    @ApiModelProperty(value = "通知类型",required = true)
    private String backType;

    @ApiModelProperty(value = "签名",required = true)
    private String sign;

}
