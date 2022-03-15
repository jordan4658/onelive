package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 汇率配置
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayExchangeRateCfg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 汇率配置ID
     */
    @TableId(value = "exchange_rate_cfg_id", type = IdType.AUTO)
    private Long exchangeRateCfgId;

    /**
     * 国家代码
     */
    private String currencyCode;

    /**
     * 提现汇率浮动值(百分比)，在当前汇率上加一些（默认0）
     */
    private String txFloatingValue;

    /**
     * 充值汇率浮动值(百分比)，在当前汇率上减一些（默认0）
     */
    private String czFloatingValue;

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

    /**
     * 是否删除
     */
    private Boolean isDelete;


}
