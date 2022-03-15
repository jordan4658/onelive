package com.onelive.common.model.vo.webSocket;

import java.io.Serializable;
import java.math.BigDecimal;

import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户发送礼物推送对象")
public class UserDetailForGiftVO implements Serializable {

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

	@ApiModelProperty("是否游客")
	private Boolean isTourists = true;

	@ApiModelProperty("送礼总数")
	private BigDecimal giftTotal;

	@ApiModelProperty("礼物详情")
	private LiveGiftDto liveGift;

	@ApiModelProperty("主播信息，全站通知时用")
	private LiveAnchorDetailVO anchorDetail;

}
