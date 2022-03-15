package com.onelive.common.model.dto.sys;

import lombok.Data;

/**
 * @author lorenzo
 * @Description: 用户与角色对应关系
 * @date 2021/4/7
 */
@Data
public class SysUserRoleDTO {

    /** 用户id*/
    private Long userId;

    /** 用户名称 */
    private String userName;

    /** 角色id */
    private Long roleId;

    /** 角色名称 */
    private String roleName;
}
