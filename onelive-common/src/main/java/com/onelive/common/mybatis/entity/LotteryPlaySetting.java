package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryPlaySetting implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类id
     */
    private Integer cateId;

    /**
     * 玩法id
     */
    private Integer playId;

    /**
     * 总注数
     */
    private Integer totalCount;

    /**
     * 中奖注数
     */
    private Integer winCount;

    /**
     * 单注金额
     */
    private Double singleMoney;

    /**
     * 投注示例
     */
    private String example;

    /**
     * 示例号码
     */
    private String exampleNum;

    /**
     * 玩法说明
     */
    private String playRemark;

    /**
     * 玩法简要说明
     */
    private String playRemarkSx;

    /**
     * 奖级
     */
    private String reward;

    /**
     * 匹配规则
     */
    private String matchtype;

    /**
     * 总注数(后端)
     */
    private Integer winCountBak;

    /**
     * 中奖注数(后端)
     */
    private Integer totalCountBak;

    /**
     * 中奖等级
     */
    private String rewardLevel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDelete;

    /**
     * 玩法规则Tag编号
     */
    private Integer playTagId;


}
