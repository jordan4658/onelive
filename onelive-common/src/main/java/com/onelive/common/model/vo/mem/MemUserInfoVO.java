package com.onelive.common.model.vo.mem;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 个人中心中的用户信息
 */
@Data
@ApiModel
public class MemUserInfoVO {

    @ApiModelProperty("用户ID")
    private Long id;

    /**
     * 用户唯一标识
     */
    @ApiModelProperty("会员ID")
    private String accno;


    /**
     * 昵称（用户与主播共用）
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 昵称修改状态 0未修改, 1已修改(后面再修改需付费)
     */
    @ApiModelProperty("昵称修改状态 true可以修改, false不可修改(后面再修改需付费)")
    private Boolean nickNameStatus;

    /**
     * 用户等级（用户与主播共用）
     */
    @ApiModelProperty("用户等级")
    private Integer userLevel;

    /**
     * 用户头像（相对路径）（用户与主播共用）
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 关注人数（用户与主播共用）
     */
    @ApiModelProperty("关注人数")
    private Integer focusNum;

    @ApiModelProperty("送出的火力,计算方式：在直播间送出的礼物总金币数 * 10")
    private String firepower = "0";

    /**
     * 粉丝数（用户与主播共用）
     */
    @ApiModelProperty("粉丝数")
    private Integer fansNum;

    /**
     * 性别 0保密 1男 2女（用户与主播共用）
     */
    @ApiModelProperty("性别 0保密 1男 2女")
    private Integer sex;

    /**
     * 生日日期
     */
    @ApiModelProperty("生日日期")
    private Date birthday;

    /**
     * 用户个性签名
     */
    @ApiModelProperty("用户个性签名")
    private String personalSignature;

    /**
     * 家乡
     */
    @ApiModelProperty("家乡")
    private String hometown;

    /**
     * 感情状态 0保密 1单身 2恋爱 3已婚
     */
    @ApiModelProperty("感情状态 0保密 1单身 2恋爱 3已婚")
    private Integer maritalStatus;

    /**
     * 职业代码, 关联职业表
     */
    @ApiModelProperty("职业代码")
    private String occupationCode;

    /**
     * 注册时候的国家code值
     */
    @ApiModelProperty("注册时候的国家code值")
    private String registerCountryCode;


    @ApiModelProperty("注册的区号")
    private String registerAreaCode;

    /**
     * 注册地区
     */
    @ApiModelProperty("注册地区")
    private String registerArea;
    /**
     * 用户选择注册区域状态, 0不可修改,1可以修改(默认为1,每个人可以修改一次)
     */
    @ApiModelProperty("用户选择注册区域状态 true可更改, false不可更改")
    private Boolean registerAreaSelectStatus;


    /**
     * 用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长
     */
    @ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private Integer userType;

    /**
     * 是否开播 只有主播开播才为true
     */
    @ApiModelProperty("是否开播")
    private Boolean isOpenLive=false;

    /**
     * 注册的手机号
     */
    @ApiModelProperty("手机号")
    private String mobilePhone;

    /**
     * 是否设置支付密码：false-否、true-是
     */
    @ApiModelProperty("是否设置支付密码：false-否、true-是")
    private Boolean isPayPassword;

    /**
     * 自动转换绝对路径
     * @return
     */
    public String getAvatar() {
        return AWSS3Util.getAbsoluteUrl(avatar);
    }
}
