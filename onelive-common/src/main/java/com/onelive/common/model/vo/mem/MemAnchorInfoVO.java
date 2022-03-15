package com.onelive.common.model.vo.mem;

import java.util.Date;

import com.onelive.common.utils.upload.AWSS3Util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("主播基础信息")
public class MemAnchorInfoVO {

	@ApiModelProperty("账号")
	private String userAccount;

	@ApiModelProperty("昵称")
	private String nickName;

	@ApiModelProperty("昵称修改状态 true可以修改, false不可修改(后面再修改需付费)")
	private Boolean nickNameStatus;

	@ApiModelProperty("用户头像")
	private String avatar;

	@ApiModelProperty("用户类型 0普通用户 1游客用户 2主播 3家族长")
	private Integer userType;

	@ApiModelProperty("最后登录时间")
	private Date lastLoginTime;

	@ApiModelProperty("注册时间")
	private Date registerTime;

	public String getAvatar() {
		return AWSS3Util.getAbsoluteUrl(avatar);
	}
}
