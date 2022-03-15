package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "主播收入传输对象")
public class AnchorIncomeMonthReq {

	@ApiModelProperty("主播的userId，家族长查询主播时传入")
	private Long userId;
	
	@ApiModelProperty("月份字符串，例：2022-10")
	private String yearMonth;
	
    @ApiModelProperty("收入类型 7-提现（成功） 14-代理佣金 20-礼物收益(主播) 21-被关注奖励(主播) 22-其他（弹幕）")
    private Integer changeType;
	
}
