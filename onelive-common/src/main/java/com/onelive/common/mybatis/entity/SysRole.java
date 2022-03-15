package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 后台系统角色
 * </p>
 *
 * @author 
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 角色id
     */
      @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态  0正常  9停用
     */
    private Integer roleStatus;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
