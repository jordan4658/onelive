package com.onelive.common.model.vo.mem;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserRiskListVO {
    private Long id;
    

    /**
     * 用户等级（用户与主播共用）
     */
    @ApiModelProperty("用户等级")
    private Integer userLevel;

    /**
     * 用户登录账号（用户与主播共用）
     */
    @ApiModelProperty("用户登录账号")
    private String userAccount;

    /**
     * 用户层级
     */
    private String group;

    /**
     * 上级账号
     */
    @ApiModelProperty("上级账号")
    private String parentUserAccount;
    
    @ApiModelProperty("会员类型")
    private String memType;
    /**
     * 注册地区
     */
    @ApiModelProperty("注册地区")
    private String registerArea;


    /**
     * 注册来源 ios、android、pc
     */
    @ApiModelProperty("设备")
    private String registerSource;
    /**
     * 注册设备
     */
    @ApiModelProperty("设备识别码")
    private String registerDevice;
    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 最后登录ip
     */
    @ApiModelProperty("最后登录ip")
    private String lastLoginIp;

}
