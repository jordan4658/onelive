package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "礼物记录传输类")
public class StudioLogLiveGiftDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 收到的礼物总额
	 */
	private BigDecimal giftTotal;

	/**
	 * 弹幕收到的总金额
	 */
	private BigDecimal barrageTotal;

}
