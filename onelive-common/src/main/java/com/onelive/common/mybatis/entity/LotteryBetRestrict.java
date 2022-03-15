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
 * 投注限制表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryBetRestrict implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 彩种ID
     */
    private Integer lotteryId;

    /**
     * 玩法tagID
     */
    private Integer playTagId;

    /**
     * 限制额度
     */
    private BigDecimal maxMoney;

    /**
     * 创建时间
     */
    private Date crateTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
