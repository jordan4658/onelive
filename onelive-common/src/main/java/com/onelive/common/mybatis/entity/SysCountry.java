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
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCountry implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 中文名称
     */
    private String zhName;

    /**
     * 英文名称
     */
    private String enName;

    /**
     * 区号
     */
    private String areaCode;

    /**
     * 国家唯一标识
     */
    private String countryCode;

    /**
     * 语言
     */
    private String lang;

    /**
     * 语言中文名称
     */
    private String langZh;

    /**
     * 当地币种
     */
    private String localCurrency;

    /**
     * 对平台币汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 当地时区
     */
    private String timeZone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认为0
     */
    private String merchantCode;

    /**
     * 是否禁用，0否1是
     */
    private Boolean isFrozen;

    /**
     * 排序号
     */
    private Integer sort;


}
