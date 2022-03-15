package com.onelive.common.model.dto.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "家长或主播查询自己的资产，包括今日收入，当月收入")
public class AnchorAssets {

	@ApiModelProperty("主播的userid")
	private Long userId;
	
    @ApiModelProperty("总资产")
    private BigDecimal totalAssets;
    
    @ApiModelProperty("当日收入")
    private BigDecimal todayIncome;
    
    @ApiModelProperty("当月收入")
    private BigDecimal monthIncome;

    
    @ApiModelProperty("关注收入")
    private BigDecimal focusMoney;
    
    @ApiModelProperty("礼物收入")
    private BigDecimal giftMoney;
    
    @ApiModelProperty("佣金收入收入")
    private BigDecimal rebatesMoney;
    
    @ApiModelProperty("其他收入(弹幕)")
    private BigDecimal otherMoney;
    
    @ApiModelProperty("客服链接")
    private String customerSerice;
    
    
    
    
    
}
