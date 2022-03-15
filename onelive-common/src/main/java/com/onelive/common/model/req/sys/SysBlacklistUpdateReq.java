package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 用户黑名单
 * </p>
 */
@Data
@ApiModel
public class SysBlacklistUpdateReq {


	@ApiModelProperty("主键")
    private Long id;

	@ApiModelProperty(value = "ip[必填]",required = true)
    private String ip;

	@ApiModelProperty(value = "状态 0-黑名单 1-非黑名单[必填]",required = true)
    private String ipStatus;
	
	@ApiModelProperty("备注")
    private String remark;
	
	@ApiModelProperty("删除用，多条")
	private List<Long> ids;


}
