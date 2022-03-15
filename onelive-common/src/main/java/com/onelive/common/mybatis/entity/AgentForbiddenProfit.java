package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  代理返点禁止记录
 * </p>
 *
 * @author ${author}
 * @since 2021-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AgentForbiddenProfit implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 是否删除(解禁时删除 0否,1是)
     */
    @TableLogic
    private Boolean isDelete;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商户编码,默认0
     */
    private String merchantCode;

    /**
     * 注册时间
     */
    private Date registTime;

    /**
     * 禁止时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


}
