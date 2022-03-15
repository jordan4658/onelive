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
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemUserAnchor implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联用户表自增id
     */
    private Long userId;

    /**
     * 家族id
     */
    private Long familyId;

    /**
     * 直播时长(每次直播结束时统计)
     */
    private Integer liveTime;

    /**
     * 直播次数
     */
    private Integer liveCount;

    /**
     * 礼物分成 例如:3 得到礼物总金额的30%
     */
    private BigDecimal giftRatio;
    
    /**
     *被关注后可以奖励钱,默认关闭
     */
    private Boolean isFocusAward;
    
    /**
     * 被关注后奖励的金额，主播有家族长时，取家族表的focus_award
     */
    private BigDecimal focusAward;

    /**
     *	被关注的奖金总额
     */
    private BigDecimal focusTotal;
    
    /**
     * 收到的礼物总额
     */
    private BigDecimal giftTotal;
    
    /**
     * 弹幕收到的总金额
     */
    private BigDecimal barrageTotal;
    
    /**
     * 代理收益的总金额
     */
    private BigDecimal rebatesTotal;

    /**
     * 已经提现的钱
     */
    private BigDecimal withdrawal;
    

    /**
     * 主播标签:针对主播个人的特色标签
     */
    private String label;

    /**
     * 开播时间，主播所在地区
     */
    private String studioOpenArea;

    /**
     * 商户code值,默认值为0
     */
    private String merchantCode;
    
    /**
     * 	公告
     */
    private String announcement;


}
