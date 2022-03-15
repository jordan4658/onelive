package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 彩种分类国际化
 * </p>
 *
 * @author ${author}
 * @since 2021-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryCategoryLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联分类ID
     */
    private Integer categoryId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
