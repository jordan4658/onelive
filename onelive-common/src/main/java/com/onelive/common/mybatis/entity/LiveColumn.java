package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

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
 * @since 2021-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveColumn implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 首页栏目ID
     */
      @TableId(value = "column_id", type = IdType.AUTO)
    private Integer columnId;

    /**
     * 栏目名称
     */
    private String columnName;
    
    /**
     * 	商户code值，默认为0
     */
    private String merchantCode;

    /**
     * 	栏目code : 关注，推荐，附近
     */
    private String columnCode;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;
    
    /**
     * 语言标识
     */
    private String lang;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 0:关闭 1:开启
     */
    private Boolean isOpen;


}
