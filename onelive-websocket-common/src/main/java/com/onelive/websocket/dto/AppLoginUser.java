package com.onelive.websocket.dto;

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
	 * 注册时候的国家唯一标识 如zh_CN、th_TH
	 */
	private String registerCountryCode;

	/**
	 * 用户所使用的国家地区编号
	 */
	private String countryCode;

	private String source;

	private String personalSignature;

	private String nickName;

	private String avatar;

	private Integer level;

	private Integer onlineUsersCount;

}
