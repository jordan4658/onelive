package com.onelive.common.model.vo.mem;

import java.io.Serializable;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemUserAnchorSearchListVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户id")
	private Long id;

	@ApiModelProperty("房间号")
	private String studioNum;
	
	@ApiModelProperty("用户唯一标识")
	private String accno;

	@ApiModelProperty("性别 0保密 1男 2女")
	private Integer sex;
	
	@ApiModelProperty("主播/用户昵称")
	private String nickName;

	@ApiModelProperty("用户头像（相对路径）")
	private String avatar;

	@ApiModelProperty("主播签名")
    private String remark;
	
	@ApiModelProperty("是否主播")
	private Boolean isAuthor;
	
	private String countryCode;

	public void setAvatar(String avatar) {
		this.avatar = AWSS3Util.getAbsoluteUrl(avatar);
	}
}
