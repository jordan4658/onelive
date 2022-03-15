package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统用户保持请求类
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysUserReq {

    @ApiModelProperty("登录账号(必须,唯一)")
    private String accLogin;

    @ApiModelProperty("昵称(必须)")
    private String userName;

    @ApiModelProperty("手机号(必须,唯一)")
    private String phone;

    @ApiModelProperty("电子邮箱(非必须)")
    private String email;

    @ApiModelProperty("密码(必须)")
    private String password;

    @ApiModelProperty("角色id (非必须)")
    private Long roleId;

    @ApiModelProperty("国家code(必须)")
    private String countryCode;

}
