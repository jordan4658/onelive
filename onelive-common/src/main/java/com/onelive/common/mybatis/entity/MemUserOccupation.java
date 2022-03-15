package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2021-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemUserOccupation implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 职业代码
     */
    private String occupationCode;

    /**
     * 职业名称
     */
    private String occupationName;

    /**
     * 是否启用 0否 1是
     */
    private Boolean status;

    /**
     * 是否删除 0否 1是
     */
    @TableLogic
    private Boolean isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
