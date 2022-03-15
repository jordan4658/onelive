package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 支付类型表
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayType implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付类型id
     */
      @TableId(value = "pay_type_id", type = IdType.AUTO)
    private Long payTypeId;

    /**
     * 支付类型code：1-支付宝、2-微信、3-银联
     */
    private Integer payTypeCode;

    /**
     * 支付类型名称
     */
    private String payTypeName;

    /**
     * 支付类型图标url
     */
    private String iconUrl;

    /**
     * 是否启用：0-否，1-是
     */
    private Boolean isEnable;

    /**
     * 是否热门：0-否，1-是
     */
    private Boolean isHot;

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


}
