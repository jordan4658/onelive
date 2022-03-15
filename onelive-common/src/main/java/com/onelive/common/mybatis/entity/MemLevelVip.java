package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * VIP等级
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemLevelVip implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 等级权重
     */
    private Integer levelWeight;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 等级图标
     */
    private String levelIcon;

    /**
     * 进场特效
     */
    private String specialEffects;

    /**
     * 晋升条件-充值
     */
    private BigDecimal promotionRecharge;

    /**
     * 昵称颜色
     */
    private String nameColor;

    /**
     * 聊天框底色
     */
    private String chatColor;

    /**
     * 直播弹幕颜色
     */
    private String barrageColor;

    /**
     * 直播发言间隔，最小为0.1（单位/秒）
     */
    private BigDecimal vipRight;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;


}
