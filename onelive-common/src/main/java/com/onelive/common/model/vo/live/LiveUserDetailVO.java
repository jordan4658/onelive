package com.onelive.common.model.vo.live;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "直播间用户详细信息")
public class LiveUserDetailVO {


    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 用户账号（用户与主播共用）
     */
    @ApiModelProperty("会员ID")
    private String accno;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;
    
    @ApiModelProperty("性别 0保密 1男 2女")
    private Integer sex;

    @ApiModelProperty("用户位置")
    //todo 创建房间时候，先默认读取主播开播时候的ip，通过ip获取位置，不行的话，则通过主播所选国家获取位置
    private String area;
    
    @ApiModelProperty("等级")
    private Integer level = 0;
    
    @ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private Integer userType;

    @ApiModelProperty("是否开播")
    private Boolean isOpenLive=false;

    @ApiModelProperty("是否关注")
    private Boolean isFocus;

    @ApiModelProperty("等级昵称")
    private String levelName;
    
    @ApiModelProperty("国家code")
    private String countryCode;
    
    @ApiModelProperty("用户注册地")
    private String registerArea;

    @ApiModelProperty("等级图标")
    private String levelIcon;

    @ApiModelProperty("关注数")
    private Integer focusNum;

	@ApiModelProperty("粉丝数")
    private Integer fansNum;

    @ApiModelProperty("送出的火力,计算方式：在直播间送出的礼物总金币数 * 10")
    private String firepower = "0";

    @ApiModelProperty("source")
    private String source;

    @ApiModelProperty("送礼总数")
    private BigDecimal giftTotal;
    
    @ApiModelProperty("用户登录账号（用户与主播共用）")
    private String userAccount;


    @ApiModelProperty("在线用户数")
    private Integer onlineUsersCount;
    
    @ApiModelProperty("商户号")
    private String merchantCode;

    /**
     * 用户个性签名
     */
    @ApiModelProperty("用户个性签名")
    private String personalSignature;

    /**
     * 生日日期
     */
    @ApiModelProperty("生日日期")
    private String birthday;

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

    //自动转绝对路径
    public String getAvatar() {
        return AWSS3Util.getAbsoluteUrl(avatar);
    }
    //自动转绝对路径
    public String getLevelIcon() {
        return AWSS3Util.getAbsoluteUrl(levelIcon);
    }
}
