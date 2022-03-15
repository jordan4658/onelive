package com.onelive.common.model.vo.pay;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PaymentVastVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/12 19:02
 */
@Data
public class PaymentVastVO {

    /**
     * 支付流水表id
     */
    private Long payId;

    /**
     * 交易时间
     */
    private Date datetime;

    /**
     * 附加字段（此字段在返回时按原样返回 (中文需要url编码)）
     */
    private String attach;

    /**
     * 金额 单位：元
     */
    private BigDecimal price;

    /**
     * 商户自定义订单号
     */
    private String orderNo;

    /**
     * 流水号
     */
    private String flowNo;

    /**
     * 支付状态：1-处理中  2-成功  3-失败 4-取消
     */
    private Integer status;



}
