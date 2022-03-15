package com.onelive.common.model.vo.pay.callback;

import lombok.Data;

/**
 * KG支付 充值回调接受实体VO
 */
@Data
public class KgDepositCallBackVO {

    /**
     * 商户代号，⼜称商户ID/接⼝ID
     */
    private String merchantCode;

    /**
     * 签名⽅法如下: - MD5: 请⽤MD5
     * Hash对请求进⾏签名计算。
     */
    private String signType;

    /**
     * 请求签名
     */
    private String sign;

    /**
     * 本栏位代表此API请求是否成功： -
     * SUCCESS: 此API请求成功 - FAIL: 此API
     * 请求失败，错误讯息请⻅message栏位
     */
    private String code;

    /**
     * 订单错误讯息
     */
    private String message;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * ⾦流平台系统订单号 (唯⼀)
     */
    private String platformOrderNo;

    /**
     * 订单⾦额。对于越南盾(VND)来说，
     * orderAmount会是个没有⼩数点的整数。
     */
    private Integer orderAmount;

    /**
     * 实际⼊帐⾦额。这个⾦额有可能跟订单⾦ 额不同。对于越南盾(VND)来说，
     * orderAmount会是个没有⼩数点的整数。
     */
    private Integer actualAmount;

    /**
     *  实际⼿续费。
     */
    private Integer actualFee;

    /**
     * 订单状态。状态是下列其中之⼀： -
     * PENDING: 订单等待中。 -
     * PROCESSING: 订单处理中。 -
     * COMPLETED: 订单完成。 -
     * ERROR: 订 单错误，请参考orderMessage。 -
     * TIMEOUT: 订单逾时。逾时的订单仍有可 能完成。 - CANCELED: 订单已取消。
     */
    private String orderStatus;


}
