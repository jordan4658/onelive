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
 * 追号记录表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryOrderAppendRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 彩种id
     */
    private Integer lotteryId;

    /**
     * 玩法id
     */
    private Integer playId;

    /**
     * 配置id
     */
    private Integer settingId;

    /**
     * 第一期期号
     */
    private String firstIssue;

    /**
     * 投注号码
     */
    private String betNumber;

    /**
     * 投注注数
     */
    private Integer betCount;

    /**
     * 单注金额
     */
    private BigDecimal betPrice;

    /**
     * 累计中奖金额
     */
    private BigDecimal winAmount;

    /**
     * 累计中奖注数
     */
    private Integer winCount;

    /**
     * 投注倍数
     */
    private Double betMultiples;

    /**
     * 翻倍倍数
     */
    private Double doubleMultiples;

    /**
     * 追号期数
     */
    private Integer appendCount;

    /**
     * 已追期数
     */
    private Integer appendedCount;

    /**
     * 类型：1 同倍追号 | 2 翻倍追号
     */
    private Integer type;

    /**
     * 中奖后停止追号：1停止 | 0不停止
     */
    private Boolean winStop;

    /**
     * 是否已停止追号
     */
    private Boolean isStop;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
