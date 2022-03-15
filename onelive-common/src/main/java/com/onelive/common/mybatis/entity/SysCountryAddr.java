package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 国际地址联动
 * </p>
 *
 * @author ${author}
 * @since 2021-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCountryAddr implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 地址名称
     */
    private String name;

    /**
     * 语言
     */
    private String lang;

    /**
     * 父级ID
     */
    private Long pid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商户code值，默认为0
     */
    private String merchantCode;


}
