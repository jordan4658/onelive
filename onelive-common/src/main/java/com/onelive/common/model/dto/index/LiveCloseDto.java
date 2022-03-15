package com.onelive.common.model.dto.index;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "关闭直播传输类")
public class LiveCloseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("本场直播收到礼物金币数量")
	private BigDecimal moneyNumber;//

	@ApiModelProperty("本场直播时长单位：秒")
	private Long liveTime;//

	@ApiModelProperty("本场直播有效观众人数")
	private Integer userCoutn;
	
	@ApiModelProperty("直播场次")
	private Integer liveCount;
}
