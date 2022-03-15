package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemAnchorWithdrawalLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 被提现的主播的user_id
     */
    private Long anchorUserId;

    /**
     * 家族长id
     */
    private Long familyId;

    /**
     * 本次被提现的金币
     */
    private BigDecimal withdrawal;

    /**
     * 家族长提现主播余额订单号,mem_family_withdrawal_log.withdrawal_log_no
     */
    private String familyWithdrawalLogNo;

    /**
     * 主播被提现金额订单号
     */
    private String withdrawalLogNo;

    /**
     * 资金流水表订单号
     */
    private String goldChangeNo;

    /**
     * 商户code值，默认为0
     */
    private String merchantCode;

    /**
     * 提款时间
     */
    private String createTime;


}
