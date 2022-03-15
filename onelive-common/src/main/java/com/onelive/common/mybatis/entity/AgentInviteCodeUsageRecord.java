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
public class AgentInviteCodeUsageRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请码所属用户ID
     */
    private Integer codeUserId;

    /**
     * 邀请码使用者用户ID, 如果没有,可以为空
     */
    private Integer usedUserId;

    /**
     * 商户编码, 默认为'0'
     */
    private String merchantCode;

    /**
     * 类型(1下载，2安装，3注册，4充值)
     */
    private Integer type;

    /**
     * 来源：pc,android,ios
     */
    private String source;

    /**
     * 设备
     */
    private String device;

    /**
     * 来源IP
     */
    private String ip;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


}
