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
 * 人工加减款记录表
 * </p>
 *
 * @author ${author}
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayHandleFinanceAmt implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 手动加减款id
     */
      @TableId(value = "handle_finance_amt_id", type = IdType.AUTO)
    private Long handleFinanceAmtId;

    /**
     * 会员账号
     */
    private String account;

    /**
     * 订单号
     */
    private String handleOrderNo;

    /**
     * 处理类型：10-减款、11-加款、12-减码、13-加码
     */
    private Integer handleType;

    /**
     * 处理金额
     */
    private BigDecimal handleNum;


    /**
     * 金额类型：1-银豆、2-金币
     */
    private Integer handleNumType;

    /**
     * 操作说明
     */
    private String handleExplain;

    /**
     * 是否删除：0-否、1-是
     */
    private Integer isDelete;

    /**
     * 是否进行打码：0-否、1-是
     */
    private Boolean isDm;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
