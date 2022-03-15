package com.onelive.common.model.vo.live;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询直播间主播详细信息")
public class LiveAnchorInfoVO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("会员ID")
    private String accno;

    @ApiModelProperty("主播昵称")
    private String nickName;

    @ApiModelProperty("主播头像")
    private String avatar;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("等级昵称")
    private String levelName;
    
    @ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private Integer userType;

    @ApiModelProperty("性别 0保密 1男 2女")
    private Integer sex;

    @ApiModelProperty("等级图标")
    private String levelIcon;

    @ApiModelProperty("当前用户是否已关注主播 false否true是")
    private Boolean isFocus;

    @ApiModelProperty("关注数")
    private Integer focusNum;

    @ApiModelProperty("粉丝数")
    private Integer fansNum;

    @ApiModelProperty("火力值,计算方式：主播在直播间收到的总金币数 * 10")
    private Integer firepower = 0;

    @ApiModelProperty("个性签名")
    private String personalSignature;

}
