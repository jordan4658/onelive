package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class MemUserUpdateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID[必填]",required = true)
    private Long id;
    /**
     * 登陆密码
     */
//    @ApiModelProperty("登陆密码")
//    private String password;

    /**
     * 确认登陆密码
     */
//    @ApiModelProperty("确认登陆密码")
//    private String repassword;
    /**
     * 用户层级id
     */
    @ApiModelProperty(value = "用户层级[必填]",required = true)
    private Long groupId;
    /**
     * 用户等级（用户与主播共用）
     */
    @ApiModelProperty(value = "用户等级[必填]",required = true)
    private Integer userLevel;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称[必填]",required = true)
    private String nickName;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 是否是直播超级管理员 0否1是
     */
    @ApiModelProperty(value = "是否是直播超级管理员 0否1是[必填]",required = true)
    private Boolean isSuperLiveManage;

    /**
     * 默认国家code值, 一般为注册国家code, 后台可修改,用于后面做提现限制
     */
    @ApiModelProperty(value = "默认国家code值, 一般为注册国家code, 后台可修改[必填]",required = true)
    private String defaultCountryCode;

    @ApiModelProperty(value = "默认国家Id, 一般为注册国家Id, 后台可修改[必填]",required = true)
    private Long countryId;

    /**
     * 开放提现的国家code, 多个code用逗号隔开
     */
    @ApiModelProperty(value = "开放提现的国家code, 多个code用逗号隔开[必填]",required = true)
    private String openCountryCode;

}
