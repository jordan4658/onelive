package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 	广告首页轮播ListVO类
 * </p>
 * 
 * @since 2021-10-19
 */
@Data
@ApiModel(value = "轮播图查询传输类")
public class SysAdvFlashviewReq {

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

}
