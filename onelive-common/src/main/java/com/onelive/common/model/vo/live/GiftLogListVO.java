package com.onelive.common.model.vo.live;

import java.math.BigDecimal;
import java.util.Date;

import com.onelive.common.mybatis.entity.LiveGiftLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 礼物详情
 */
@Data
@ApiModel
public class GiftLogListVO extends LiveGiftLog {

	private static final long serialVersionUID = 1096984584236504206L;
	
	@ApiModelProperty("用户账号")
	private String userAccount;
	
	@ApiModelProperty("订单号")
	private String orderNo; 
	
	@ApiModelProperty("提交时间")
    private Date givingTime;
	
	@ApiModelProperty("礼物名称")
    private String giftName;
	
	@ApiModelProperty("订阅主播")
    private String givingName;
	
	@ApiModelProperty("金额")
    private BigDecimal amount;
}
