package com.onelive.common.model.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class AppLoginVO {

    @ApiModelProperty(value = "登录token")
    private String acctoken;

    @ApiModelProperty(value = "用户账号")
    private String accno;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "等级")
    private Integer userLevel;

    @ApiModelProperty(value = "用户拥有的平台币")
    private BigDecimal amount = BigDecimal.ZERO.setScale(3, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "注册时间戳")
    private Date registerTime;

    @ApiModelProperty(value = "注册的区号,如：+86")
    private String areaCode;

    @ApiModelProperty(value = "注册的手机号")
    private String mobilePhone = "";

    @ApiModelProperty("性别 0保密 1男 2女")
    private Integer sex = 0;

    @ApiModelProperty("生日日期")
    private String birthday = "";

    @ApiModelProperty("用户所属国家code值")
    private String countryCode;


}
