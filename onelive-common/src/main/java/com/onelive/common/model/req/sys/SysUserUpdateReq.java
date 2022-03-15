package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统用户编辑请求
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysUserUpdateReq {

    @ApiModelProperty("系统用户id")
    private String userId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("角色id")
    private Long roleId;
    /**
     * 国家sys_country.country_code
     */
    @ApiModelProperty("国家code")
    private String countryCode;
}
