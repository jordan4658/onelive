package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录密码（用户与主播共用）
     */
    private String password;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 密码盐值（用户与主播共用）
     */
    private String salt;

    /**
     * 所属地区id（用户与主播共用）
     */
    private Long countryId;

    /**
     * 昵称（用户与主播共用）
     */
    private String nickName;

    /**
     * 备注（用户与主播共用）
     */
    private String remark;

    /**
     * 性别 0保密 1男 2女（用户与主播共用）
     */
    private Integer sex;

    /**
     * 用户登录账号（用户与主播共用）
     */
    private String userAccount;

    /**
     * 关注人数（用户与主播共用）
     */
    private Integer focusNum;

    /**
     * 粉丝数（用户与主播共用）
     */
    private Integer fansNum;

    /**
     * 是否冻结 0否1是（用户与主播共用）
     */
    private Boolean isFrozen;

    /**
     * 用户等级（用户与主播共用）
     */
    private Integer userLevel;

    /**
     * 用户头像（相对路径）（用户与主播共用）
     */
    private String avatar;

    /**
     * 注册的手机号
     */
    private String mobilePhone;

    /**
     * 注册的区号
     */
    private String registerAreaCode;

    /**
     * 用户唯一标识
     */
    private String accno;

    /**
     * 用户层级id
     */
    private Long groupId;

    /**
     * 生日日期
     */
    private Date birthday;

    /**
     * 注册ip
     */
    private String registerIp;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 创建人(后台可创建用户)
     */
    private String createdBy;

    /**
     * 注册地区
     */
    private String registerArea;

    /**
     * 注册来源 ios、android、pc
     */
    private String registerSource;

    /**
     * 注册设备
     */
    private String registerDevice;

    /**
     * 注册时候的国家code值
     */
    private String registerCountryCode;

    /**
     * 用户选择注册区域状态, 0不可修改,1可以修改(默认为1,每个人可以修改一次)
     */
    private Boolean registerAreaSelectStatus;

    /**
     * 默认国家code值, 一般为注册国家code, 后台可修改,用于后面做提现限制
     */
    private String defaultCountryCode;

    /**
     * 开放提现的国家code, 多个code用逗号隔开, 如果是全部设置为all
     */
    private String openCountryCode;

    /**
     * 最后登录ip
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录来源  ios、android、pc
     */
    private String lastLoginSource;

    /**
     * 最后登录地区
     */
    private String lastLoginArea;

    /**
     * 最后登录设备
     */
    private String lastLoginDevice;

    /**
     * 是否返点 0否1是
     */
    private Boolean isCommission;

    /**
     * 是否允许投注 0否1是
     */
    private Boolean isBet;

    /**
     * 是否允许出款 0否1是
     */
    private Boolean isDispensing;

    /**
     * 是否是直播超级管理员 0否1是
     */
    private Boolean isSuperLiveManage;

    /**
     * 是否在线 0否1是
     */
    private Boolean isOnline;

    /**
     * 用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长
     */
    private Integer userType;

    /**
     * 注册游客时候的设备标识
     */
    private String userDeviceId;

    /**
     * 用户个性签名
     */
    private String personalSignature;

    /**
     * 家乡
     */
    private String hometown;

    /**
     * 感情状态 0保密 1单身 2恋爱 3已婚
     */
    private Integer maritalStatus;

    /**
     * 职业代码, 关联职业表
     */
    private String occupationCode;

    /**
     * 昵称修改状态 1可以修改, 0不可修改(后面再修改需付费)
     */
    private Boolean nickNameStatus;

    /**
     * 手机认证状态, 0未认证 1已认证
     */
    private Boolean mobileAuthenticated;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认为0
     */
    private String merchantCode;


}
