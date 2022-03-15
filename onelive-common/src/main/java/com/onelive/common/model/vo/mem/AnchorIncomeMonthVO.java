package com.onelive.common.model.vo.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "主播月收支传输对象")
public class AnchorIncomeMonthVO {
	
	@ApiModelProperty("收入金额，根据收入类型统计")
	private BigDecimal income;
	
	@ApiModelProperty("支出金额")
	private BigDecimal expend;
	
}
