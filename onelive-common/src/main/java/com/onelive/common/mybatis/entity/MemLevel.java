package com.onelive.common.mybatis.entity;

import com.onelive.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户层级表
 * </p>
 *
 * @author kevin
 * @since 2021-10-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MemLevel extends BaseEntity<MemLevel> {

    private static final long serialVersionUID = 1L;

    /**
     * 层级名称
     */
    private String name;

    /**
     * 入款次数限制
     */
    private Long depositTimesLimit;

    /**
     * 单次入款金额限制
     */
    private BigDecimal singleDepositLimit;

    /**
     * 总入款金额限制
     */
    private BigDecimal totalDepositLimit;

    /**
     * 最大出款次数限制
     */
    private Long withdrawalTimesLimit;

    /**
     * 出款总额限制
     */
    private BigDecimal totalWithdrawalLimit;

    /**
     * 有效开始时间
     */
    private Date efficientStartTime;

    /**
     * 有效结束时间
     */
    private Date efficientEndTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序号
     */
    private Integer sort;


}
