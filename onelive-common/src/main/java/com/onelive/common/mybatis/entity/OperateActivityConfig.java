package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 活动配置表
 * </p>
 *
 * @author ${author}
 * @since 2021-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperateActivityConfig implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 跳转地址
     */
    private String skipUrl;

    /**
     * 配置内容
     */
    private String config;

    /**
     * 活动状态 0否 1是
     */
    private Boolean isActive;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 后台创建人账号
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 后台更新人账号
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
