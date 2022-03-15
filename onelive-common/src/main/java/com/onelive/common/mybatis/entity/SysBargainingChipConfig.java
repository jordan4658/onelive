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
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysBargainingChipConfig implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 筹码1
     */
    private BigDecimal bargainingChip1;

    /**
     * 筹码2
     */
    private BigDecimal bargainingChip2;

    /**
     * 筹码3
     */
    private BigDecimal bargainingChip3;

    /**
     * 筹码4
     */
    private BigDecimal bargainingChip4;

    /**
     * 筹码5
     */
    private BigDecimal bargainingChip5;

    /**
     * 筹码6
     */
    private BigDecimal bargainingChip6;

    /**
     * 筹码7
     */
    private BigDecimal bargainingChip7;

    /**
     * 是否启用 0否 1是
     */
    private Boolean status;

    /**
     * 是否显示筹码单位 0否, 1是
     */
    private Boolean showUnit;

    /**
     * 用户自定义筹码最小值
     */
    private BigDecimal customizeMin;

    /**
     * 用户自定义筹码最大值
     */
    private BigDecimal customizeMax;

    /**
     * 下注最小值
     */
    private BigDecimal bargainingChipMin;

    /**
     * 下注最大值
     */
    private BigDecimal bargainingChipMax;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 后台创建人账号
     */
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 后台更新人账号
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
