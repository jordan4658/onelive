package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "单场直播收费详情")
public class LiveIncomeDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("单次直播的记录id")
	private Integer logId;

	@ApiModelProperty("礼物收入")
	private BigDecimal giftMoney;
	
	@ApiModelProperty("门票收入")
	private BigDecimal watchMoney;
	
	@ApiModelProperty("弹幕收入")
	private BigDecimal barrageMoney;
	
	
	
	// 二期
	@ApiModelProperty("打赏收入")
	private BigDecimal guardMoney;
	
	@ApiModelProperty("守护收入")
	private BigDecimal focusMoney;
	
	@ApiModelProperty("商户")
	private String merchantCode = "0";

}
