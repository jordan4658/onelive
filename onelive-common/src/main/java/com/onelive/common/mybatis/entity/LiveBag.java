package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveBag implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 英语名称
     */
    private String bagName;

    /**
     * 类型 1.座驾 2.活动礼品
     */
    private Integer type;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片
     */
    private String img;

    /**
     * 动画
     */
    private String animation;

    /**
     * 动画类型 1.svga动画 2.lottie动画 3.骨骼动画
     */
    private Integer animationType;

    /**
     * 动画状态 0静态，1动态
     */
    private Integer animationStatus;

    /**
     * 座驾停留时间(单位: 秒)
     */
    private Integer retentionTime;

    /**
     * 地区国家code列表
     */
    private String countryCodeList;

    /**
     * 地区名称列表
     */
    private String countryNameList;

    /**
     * 状态 0启用1禁用
     */
    private Boolean isFrozen;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人(后台操作人登录账号)
     */
    private String createUser;

    /**
     * 修改人(后台操作人登录账号)
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
