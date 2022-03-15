package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统用户ListVO类
 * @date 2021/4/6
 */
@Data
@ApiModel
public class SysUserListVO {

    @ApiModelProperty("系统用户主键")
    private Long userId;

    @ApiModelProperty("系统用户名称")
    private String userName;

    @ApiModelProperty("登录账号")
    private String accLogin;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("账号状态 0启用 9禁用")
    private Integer accStatus;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("国家code")
    private String countryCode;
}
