package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 支付信息
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayMentInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付id
     */
      @TableId(value = "pay_id", type = IdType.AUTO)
    private Long payId;

    /**
     * 支付标示第三方支付标示号，如支付宝的订单号或微信的prepay_id等
     */
    private String payCode;

    /**
     * 支付时间
     */
    private Date payStartTime;

    /**
     * 支付完成时间
     */
    private Date payEndTime;

    /**
     * 流水号
     */
    private String serialNo;

    /**
     * 会员标识号
     */
    private String accno;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 支付渠道id
     */
    private Long payWayId;

    /**
     * 支付方式标识：H5、WAP、JSAPI
     */
    private String payType;

    /**
     * 支付金额
     */
    private BigDecimal payAmt;

    /**
     * 支付状态：0-支付成功、1-支付中、2-支付失败
     */
    private Integer payStatus;

    /**
     * 支付错误描述 不用格式自己定义，如微信支付可以存错误代码$$错误描述
     */
    private String payErrdesc;

    /**
     * 系统代码：来源系统代码
     */
    private String systemName;

    /**
     * 支付标示二维码(页面)
     */
    private String payCodeUrl;

    /**
     * 备注 
     */
    private String payNote;

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
     * 商户配置ID
     */
    private Long providerId;


}
