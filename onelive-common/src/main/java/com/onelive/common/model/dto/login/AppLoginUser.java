package com.onelive.common.model.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName LoginUser
 * @Desc app登录人基本信息，
 * @Date 2021/3/16 10:26
 */
@Data
public class AppLoginUser {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户唯一标识
     */
    private String accno;
    /**
     * 注册时候的手机号
     */
    private String mobilePhone;
    /**
     * 手机区号
     */
    private String areaCode;
    /**
     * 用户登录账号
     */
    private String userAccount;
    /**
     * 	登陆时的app语言
     */
    private String lang;
    /**
     * 商户code值，默认为0(平台)
     */
    private String merchantCode;
    
    /**
     * 用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长
     */
    private Integer userType;

    /**
     * 注册时候的国家唯一标识 如zh_CN、th_TH
     */
    private String registerCountryCode;

    /**
     * 用户所使用的国家地区编号
     */
    private String countryCode;

    @ApiModelProperty("source")
    private String source;

    @ApiModelProperty("用户个性签名")
    private String personalSignature;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;
    
    @ApiModelProperty("等级")
    private Integer level;
    
    @ApiModelProperty("直播间在线人数")
    private Integer onlineUsersCount;

}    
    