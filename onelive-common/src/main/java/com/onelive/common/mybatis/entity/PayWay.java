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
 * 支付方式
 * </p>
 *
 * @author ${author}
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayWay implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付方式主键id
     */
      @TableId(value = "pay_way_id", type = IdType.AUTO)
    private Long payWayId;

    /**
     * 支付商id 
     */
    private Long providerId;

    /**
     * 国家编码（区分支付方式是那个国家的）
     */
    private String countryCode;

    /**
     * 支付类型code：1-支付宝、2-微信、3-银联 (pay_type表中pay_type_code )
     */
    private Integer payTypeCode;

    /**
     * 支付方式的名称
     */
    private String payWayName;

    /**
     * 支付方式标识： 线下-(BANK)、线上-(H5、WAP、JSAPI)
     */
    private String payWayTag;

    /**
     * 支付方式图标
     */
    private String payWayIcon;

    /**
     * 赠送类型： 0-不赠送，1-首充赠送，2-每次赠送
     */
    private Integer givingType;

    /**
     * 赠送比例 %
     */
    private BigDecimal payWayGivingRatio;

    /**
     * 单笔最低充值金额
     */
    private BigDecimal minAmt;

    /**
     * 单笔最高充值金额
     */
    private BigDecimal maxAmt;

    /**
     * 快捷充值选项，多个使用“,”分隔开
     */
    private String shortcut;

    /**
     * 是否允许输入金额：0-否，1-是
     */
    private Boolean isInput;

    /**
     * 状态：1-启用，2-禁用
     */
    private Integer status;

    /**
     * 排序--每个支付类型下的支付方式排序
     */
    private Long sortBy;

    /**
     * 创建人账号
     */
    private String createUser;

    /**
     * 最后更新人账号
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
