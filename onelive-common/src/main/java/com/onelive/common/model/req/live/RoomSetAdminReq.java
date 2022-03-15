package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间设置/取消管理员")
public class RoomSetAdminReq {

	@ApiModelProperty("被设置/取消管理的用户id  [必填]")
	private Long adminId;
	
	@ApiModelProperty("设置成管理员/取消管理员 true：设置 false：取消  [必填]")
	private Boolean isAdmin;
	
	
}
