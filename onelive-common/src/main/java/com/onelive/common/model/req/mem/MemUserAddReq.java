package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemUserAddReq implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 上级账号
     */
    @ApiModelProperty(value = "上级账号")
    private String parentUserAccount;

    /**
     * 用户唯一标识 (ID)
     */
//    @ApiModelProperty("ID")
//    private String accno;

    /**
     * 注册的区号
     */
    @ApiModelProperty(value = "注册的区号[必填]",required = true)
    private String registerAreaCode;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "登陆手机号[必填]",required = true)
    private String mobilePhone;

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
    @ApiModelProperty(value = "备注")
    private String remark;


}
