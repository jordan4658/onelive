package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑用户信息时查询的内容
 */
@Data
@ApiModel
public class MemUserVO {

    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 用户唯一标识
     */
    @ApiModelProperty("用户唯一标识")
    private String accno;

    /**
     * 用户登录账号（用户与主播共用）
     */
    @ApiModelProperty("用户登录账号")
    private String userAccount;

    /**
     * 昵称（用户与主播共用）
     */
    @ApiModelProperty("昵称")
    private String nickName;

    /**
     * 注册地区
     */
    @ApiModelProperty("注册地区")
    private String registerArea;


    @ApiModelProperty("注册地区号")
    private String registerAreaCode;


    /**
     * 上级账号
     */
    @ApiModelProperty("所属上级账号")
    private String parentUserAccount;

    /**
     * 用户等级（用户与主播共用）
     */
    @ApiModelProperty("用户等级")
    private Integer userLevel;

    @ApiModelProperty("用户层级")
    private Long groupId;

    @ApiModelProperty("是否是直播超级管理员 0否1是")
    private Boolean isSuperLiveManage;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * 默认国家code值, 一般为注册国家code, 后台可修改,用于后面做提现限制
     */
    @ApiModelProperty("默认国家code值, 一般为注册国家code, 后台可修改,用于后面做提现限制")
    private String defaultCountryCode;

    /**
     * 开放提现的国家code, 多个code用逗号隔开, 如果是全部设置为all
     */
    @ApiModelProperty("开放提现的国家code, 多个code用逗号隔开, 如果是全部设置为all")
    private String openCountryCode;

}
