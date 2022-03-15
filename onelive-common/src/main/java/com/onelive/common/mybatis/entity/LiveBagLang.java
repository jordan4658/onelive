package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveBagLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联背包ID
     */
    private Long bagId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 背包物品名称
     */
    private String bagName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
