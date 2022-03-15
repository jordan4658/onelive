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
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AgentInviteCode implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 邀请码(需要保证唯一)
     */
    private String inviteCode;

    /**
     * 代理层级, 没有上线的是一级代理
     */
    private Integer agentLevel;

    /**
     * 邀请用户ID
     */
    private Long inviteUserId;

    /**
     * 邀请用户账号
     */
    private String inviteUserAccount;

    /**
     * 根用户ID,记录最初分享的用户ID, 没有上级的用户就是根用户, 记录自己ID
     */
    private Long rootUserId;

    /**
     * 商户编码,默认0
     */
    private String merchantCode;

    /**
     * 状态:1正常,0停用
     */
    private Boolean status;

    /**
     * 是否删除(0否,1是)
     */
    @TableLogic
    private Boolean isDelete;

    /**
     * 是否系统生成(0否,1是)
     */
    private Boolean isSys;

    /**
     * 是否自动生成:1是(用户注册生成) 0否(后台创建用户生成)
     */
    private Boolean isAutoCreate;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


}
