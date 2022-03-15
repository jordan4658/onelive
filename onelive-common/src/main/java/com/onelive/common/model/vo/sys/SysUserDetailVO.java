package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统用户详情VO类
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysUserDetailVO {

    @ApiModelProperty("系统用户主键")
    private Long userId;

    @ApiModelProperty("登录账号")
    private String accLogin;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String userName;

    @ApiModelProperty("角色id")
    private Long roleId;

    /**
     * 国家sys_country.country_code
     */
    @ApiModelProperty("国家 code")
    private String countryCode;

}
