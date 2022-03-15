package com.onelive.common.model.vo.webSocket;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户发送弹幕推送对象")
public class UserDetailForBarrageVO implements Serializable {

	private static final long serialVersionUID = 6218380711853423563L;

	@ApiModelProperty("会员id")
	private String userId;

	@ApiModelProperty("用户昵称")
	private String nickName;

	@ApiModelProperty("用户头像")
	private String avatar;

	@ApiModelProperty("性别 0保密 1男 2女")
	private Integer sex;

	@ApiModelProperty("用户位置")
	// todo 创建房间时候，先默认读取主播开播时候的ip，通过ip获取位置，不行的话，则通过主播所选国家获取位置
	private String area;

	@ApiModelProperty("等级")
	private Integer level = 0;

	@ApiModelProperty("等级昵称")
	private String levelName;

	@ApiModelProperty("等级图标")
	private String levelIcon;

	@ApiModelProperty("是否游客")
	private Boolean isTourists = true;

	@ApiModelProperty("用户备注内容")
	private String remark;

	@ApiModelProperty("关注数")
	private Integer focusNum;

	@ApiModelProperty("粉丝数")
	private Integer fansNum;

	@ApiModelProperty("送出的火力,计算方式：在直播间送出的礼物总金币数 * 100")
	private String firepower = "0";

	@ApiModelProperty("送礼总数")
	private BigDecimal giftTotal;

	@ApiModelProperty("弹幕内容")
	private String barrage;
}
