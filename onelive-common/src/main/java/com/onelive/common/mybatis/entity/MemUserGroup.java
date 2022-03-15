package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户层级表
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemUserGroup implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户层级主建ID
     */
      @TableId(value = "user_group_id", type = IdType.AUTO)
    private Long userGroupId;

    /**
     * 国家编码
     */
    private String currencyCode;

    /**
     * 用户层级名称
     */
    private String groupName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否邀请返点：0-否、1-是
     */
    private Boolean isInvitationRebates;

    /**
     * 出款次数（默认0）
     */
    private Long withdrawalsTimes;

    /**
     * 入款次数（默认99999999）
     */
    private Long depositTimes;

    /**
     * 生效开始时间
     */
    private Date startTime;

    /**
     * 生效结束时间
     */
    private Date endTime;

    /**
     * 是否删除 0-否、1-是
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
