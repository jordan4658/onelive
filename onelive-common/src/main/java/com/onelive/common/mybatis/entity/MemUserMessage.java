package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户消息表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemUserMessage implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 消息ID
     */
    private Long msgId;

    /**
     * 消息类型 1.系统消息 2.邮件
     */
    private Integer msgType;

    /**
     * 是否已推送 0否1是
     */
    private Boolean isPush;

    /**
     * 是否已读 0否1是
     */
    private Boolean isRead;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
