package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "公告查询")
public class SysAdvNoticeTypeReq {

	@ApiModelProperty("1：直播间公告  2：开场公告（一个语言只有一条）\"3：循环公告  4：首页公告 5：推荐栏目路马灯公告  6：游戏列表路马灯公告 7:兑换银豆公告 8：充值中心")
	private Integer type;

}
