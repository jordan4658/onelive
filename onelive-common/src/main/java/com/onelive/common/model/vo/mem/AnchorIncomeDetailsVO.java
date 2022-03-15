package com.onelive.common.model.vo.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "主播收入传输对象")
public class AnchorIncomeDetailsVO {

	@ApiModelProperty("主播的userid")
	private Long userId;
	
	//	礼物收益非实时到账，待家族长分配，其他收入类型实时到账
	@ApiModelProperty("收入类型 7-提现（成功） 14-代理佣金 20-礼物收益(主播) 21-被关注奖励(主播) 22-其他（弹幕，弹幕）")
    private Integer changeType;
	
	@ApiModelProperty("创建时间")
    private String createTime;
	
	@ApiModelProperty("收入类型名（大字）")
	private String changeName;
	
	@ApiModelProperty("收入金额")
	private BigDecimal changeMoney;
	
	
}
