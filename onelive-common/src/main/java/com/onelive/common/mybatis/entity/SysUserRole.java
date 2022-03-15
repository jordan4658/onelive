package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台用户角色关系
 * </p>
 *
 * @author 
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户角色关系id
     */
      @TableId(value = "user_role_id", type = IdType.AUTO)
    private Long userRoleId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 会员标识号
     */
    private Long userId;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
