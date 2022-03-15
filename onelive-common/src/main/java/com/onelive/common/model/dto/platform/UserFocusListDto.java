package com.onelive.common.model.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户关注的主播列表返回")
public class UserFocusListDto {

	@ApiModelProperty("关注的主播用户昵称")
	private String nickName;

	@ApiModelProperty("关注的主播用户头像（缩略图）")
	private String thumbHeadImage;

	@ApiModelProperty("公告")
	private String announcement;

	@ApiModelProperty("直播间状态 0：未开播、1：开播")
	private Integer studioStatus;

	@ApiModelProperty("直播间封面图（缩略图）")
	private String studioThumbImage;

	@ApiModelProperty("直播间拉流地址")
	private String studioLivePath;

	@ApiModelProperty("直播间推流地址")
	private String studioLivePushFlow;

	@ApiModelProperty(" 直播间封面图（大图）")
	private String studioBackground;

	@ApiModelProperty(" 直播间标题")
	private String studioTitle;

	@ApiModelProperty("直播间ID")
	private String studioId;

	@ApiModelProperty("是否提醒")
	private Integer isRemind;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

}