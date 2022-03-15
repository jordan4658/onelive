package com.onelive.common.model.req.pay.callback;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName VgPayCallBackReq
 * @Description TODO
 * @Author wk
 * @Date 2021/6/7 10:53
 **/
@Data
@ApiModel
public class VgPayCallBackReq {

    @ApiModelProperty(value = "⽀付平台⽣成的充币付款ID")
    private String paymentID;

    @ApiModelProperty(value = "商户用户标识")
    private String clientUid;

    @ApiModelProperty(value = "提币申请ID")
    private String orderID;

    @ApiModelProperty(value = "加密货币数量")
    private BigDecimal amount;

    @ApiModelProperty(value = "实际到账数量")
    private BigDecimal arrivalAmount;

    @ApiModelProperty(value = "兑换获取vhkd⾦额")
    private BigDecimal money;

    @ApiModelProperty(value = "状态：0、未充币 7、撤销 9、确认 中 10、已到账 13、⼩于系 统最⼩充值记录")
    private Integer status;

    @ApiModelProperty(value = "⽬标地址")
    private String address;

    @ApiModelProperty(value = "币种")
    private String coin;

    @ApiModelProperty(value = "链交易ID")
    private String txid;

    @ApiModelProperty(value = "接收时间")
    private Long receivedTime;

    @ApiModelProperty(value = "商户扩展数据")
    private String exdata;

    @ApiModelProperty(value = "签名")
    private String mac;
}
