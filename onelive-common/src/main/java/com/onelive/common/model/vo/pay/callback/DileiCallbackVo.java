package com.onelive.common.model.vo.pay.callback;

import lombok.Data;

@Data
public class DileiCallbackVo {
    /**
     * 商户号
     */
    private String memberid;
    /**
     * 平台订单号
     */
    private String orderid;
    /**
     *订单金额 单位：元
     */
    private String amount;
    /**
     * 交易流水号 商户订单号
     */
    private String transaction_id;
    /**
     * 交易时间
     */
    private String datetime;
    /**
     * 交易状态 “00” 为成功
     */
    private String returncode;
    /**
     * 附加字段（此字段在返回时按原样返回 (中文需要url编码)）
     */
    private String attach;
    /**
     * 签名
     */
    private String sign;

}