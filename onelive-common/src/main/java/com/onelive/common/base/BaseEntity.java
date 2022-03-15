package com.onelive.common.base;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * BaseEntity
 *
 * @author kevin
 * @version 1.0.0
 * @since 2021年10月27日 下午7:56:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public abstract class BaseEntity<T extends Model<T>> extends Model<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键（字段名）
     */
    public static final String ID = "id";
    /**
     * 版本号（字段名）
     */
    public static final String VERSION = "version";
    /**
     * 创建时间（字段名）
     */
    public static final String CREATE_TIME = "create_time";
    /**
     * 更新时间（字段名）
     */
    public static final String UPDATE_TIME = "update_time";

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 版本号
     */
    @Version
    @TableField(value = "version")
    private Integer version;

    /**
     * pkVal
     *
     * @return Serializable
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:56:28
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}

