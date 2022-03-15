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
 * @since 2022-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperateMessage implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 接收类型 1层级 2等级 3地区 4账号
     */
    private Integer receiveType;

    /**
     * 接收者(多个用逗号分隔)
     */
    private String receiver;

    /**
     * 消息类型 1.系统消息 2.邮件
     */
    private Integer msgType;

    /**
     * 图片地址
     */
    private String img;

    /**
     * 跳转链接
     */
    private String skipUrl;

    /**
     * 是否删除 0否 1是
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;

    /**
     * 最后推送用户ID
     */
    private Long lastUserId;


}
