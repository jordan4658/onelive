package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 			短信开关
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
@Data
@ApiModel(value = "短信开关")
public class SysShortMsgSwitchReq {
	
	@ApiModelProperty("短信开关 1开启，0关闭")
	private int isOpen;
	

}
