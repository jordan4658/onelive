package com.onelive.common.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户层级 出入款配置
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemGroupCurrencyCfg implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主建id
     */
      @TableId(value = "group_currency_id", type = IdType.AUTO)
    private Long groupCurrencyId;

    /**
     * 用户层级ID
     */
    private Long userGroupId;

    /**
     * 国家
     */
    private String currencyCode;

    /**
     * 最大存款金额
     */
    private BigDecimal maxDeposit;

    /**
     * 入款总额
     */
    private BigDecimal totalDeposit;

    /**
     * 出款总额
     */
    private BigDecimal totalDispensing;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新人
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


    /**
     * 是否删除 0-否、1-是
     */
    private Boolean isDelete;


}
