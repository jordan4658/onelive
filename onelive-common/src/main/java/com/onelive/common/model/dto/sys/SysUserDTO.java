package com.onelive.common.model.dto.sys;


import lombok.Data;

@Data
public class SysUserDTO {
    /**
     * 系统用户主键
     */
    private Long userId;

    /**
     * 系统用户名称
     */
    private String userName;

    /**
     * 登录账号
     */
    private String accLogin;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 账号状态
     */
    private Integer accStatus;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 国家 code
     */
    private String countryCode;
}
