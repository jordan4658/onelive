package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间主播详细信息")
public class LiveAnchorDetailVO {

    @ApiModelProperty("会员id")
    private Long userId;

    @ApiModelProperty("主播昵称")
    private String nickName;
    
    @ApiModelProperty("主播账号")
    private String userAccount;

    @ApiModelProperty("主播头像")
    private String avatar;
    
    @ApiModelProperty("签名")
    private String personalSignature;
    
    @ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private Integer userType;

    @ApiModelProperty("主播位置")
    //todo 创建房间时候，先默认读取主播开播时候的ip，通过ip获取位置，不行的话，则通过主播所选国家获取位置
    private String studioOpenArea;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("等级昵称")
    private String levelName;
    
    @ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id）")
	private Integer productId;
    
    @ApiModelProperty("房间号")
	private String studioNum;
    
    @ApiModelProperty("主播公告")
    private String announcement;
    
    @ApiModelProperty("性别 0保密 1男 2女")
    private Integer sex;

    @ApiModelProperty("等级图标")
    private String levelIcon;

    @ApiModelProperty("主播备注内容")
    private String remark;

    @ApiModelProperty("当前用户是否已关注主播 false否true是")
    private Boolean isFocus;

    @ApiModelProperty("关注数")
    private Integer focusNum;

    @ApiModelProperty("粉丝数")
    private Integer fansNum;

    @ApiModelProperty("火力值,计算方式：主播在直播间收到的总金币数 * 10")
    private Integer firepower;

	@ApiModelProperty("画质")
	private String sharpness;

	@ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
	private Integer studioStatus;

	@ApiModelProperty("国家sys_country.id")
	private Integer countryId;
	
	@ApiModelProperty("国家名")
	private String countryName;
	
	@ApiModelProperty("默认0:绿播 1:黄播")
	private Integer colour;

	@ApiModelProperty("首页栏目ID—关联— live_column.columnCode")
	private String columnCode;

}
