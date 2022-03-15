package com.onelive.common.model.req.mem;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemUserListReq extends PageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识 (ID)
     */
    @ApiModelProperty(value = "会员ID")
    private String accno;
    /**
     * 地区 查询用户的注册地区code
     */
    @ApiModelProperty("地区code")
    private String countryCode;
    /**
     * 账号前缀
     */
    @ApiModelProperty("账号前缀")
    private String accountPrefix;
    /**
     * 登陆账号
     */
    @ApiModelProperty("登陆账号")
    private String userAccount;
    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;
    /**
     * 上级账号
     */
    @ApiModelProperty("上级账号")
    private String parentUserAccount;

    /**
     * 用户等级（用户与主播共用）
     */
    @ApiModelProperty("用户等级")
    private Integer userLevel;

    @ApiModelProperty("用户层级")
    private Integer groupId;
    /**
     * 用户状态 是否冻结 0否1是（用户与主播共用）
     */
    @ApiModelProperty("用户状态")
    private Boolean isFrozen;

    /**
     * ip地址
     */
    @ApiModelProperty("登陆ip地址")
    private String lastLoginIp;

    @ApiModelProperty("商户code值，默认值为0")
    private String merchantCode;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

}
