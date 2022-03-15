package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单信息主
 * </p>
 *
 * @author ${author}
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayOrderRecharge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 支付方式id
     */
    private Long payWayId;

    /**
     * 订单类型 1-线上充值 2-线下充值
     */
    private Integer orderType;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 国家code
     */
    private String currencyCode;

    /**
     * 实际付款金额（对应国家币种）
     */
    private BigDecimal actualPayment;

    /**
     * 充值汇率（平台币兑换国家币的汇率）
     */
    private String exchange;

    /**
     * 会员标识号
     */
    private String account;

    /**
     * 第三方预支付id，如支付宝的订单号或微信的prepay_id等
     */
    private String prepayId;

    /**
     * 支付方式标识： 线下-(BANK)、线上-(H5、WAP、JSAPI)
     */
    private String payWayTag;

    /**
     * 金币数量
     */
    private BigDecimal sumAmt;

    /**
     * 订单状态 1-处理中  2-成功  3-失败 4-取消
     */
    private Integer orderStatus;

    /**
     * 失败、取消订单原因
     */
    private String cancelReason;

    /**
     * 支付时间
     */
    private Date payDate;

    /**
     * 汇款姓名
     */
    private String payUser;

    /**
     * 汇款备注
     */
    private String payNote;

    /**
     * 订单备注
     */
    private String orderNote;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 来源 Android | IOS | WEB
     */
    private String source;

    /**
     * 是否首充 false否true是
     */
    private Boolean isFirst;


}
