package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AgentInviteRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 	新注册用户ID
     */
    private Long userId;

    /**
     * 邀请码(没有设置为系统邀请platform)
     */
    private String inviteCode;

    /**
     * 	邀请码所属用户ID,0表示系统(没有邀请码)
     */
    private Long inviteUserId;

    /**
     * 商户编码，默认0
     */
    private String merchantCode;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


}
