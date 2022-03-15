package com.onelive.common.model.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物报表类")
public class LiveGifReportDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("礼物id")
	private Integer giftId;

	@ApiModelProperty("礼物名字")
	private String giftName;

	@ApiModelProperty("购买总额")
	private BigDecimal price;
	
	@ApiModelProperty("购买人数")
	private Integer  peopleBuyCount;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

}
