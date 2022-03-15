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
 * @since 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameIndexLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 语言
     */
    private String lang;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标url
     */
    private String iconUrl;

    /**
     * 关联game_index.id
     */
    private Long gameIndexId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商户号
     */
    private String merchantCode;


}
