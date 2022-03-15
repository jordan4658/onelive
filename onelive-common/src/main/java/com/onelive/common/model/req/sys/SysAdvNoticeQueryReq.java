package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 首页公告查询请求类
 */
@Data
@ApiModel
public class SysAdvNoticeQueryReq {

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;
    
	@ApiModelProperty("1：直播间公告  2：开场公告（一个语言只有一条） " + 
			"3：循环公告  4：首页公告 5：推荐栏目路马灯公告  6：游戏列表路马灯公告")
	private Integer type;

}
