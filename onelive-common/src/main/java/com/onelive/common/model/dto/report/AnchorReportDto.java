package com.onelive.common.model.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "主播报表类")
public class AnchorReportDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("id")
	private Long id;
	
	@ApiModelProperty("主播的用户id")
	private Long userId;

	@ApiModelProperty("主播账号")
	private String userAccount;
	
	@ApiModelProperty("主播昵称")
	private String nickName;

	@ApiModelProperty("注册人数")
	private Integer  registerCount;
	
	@ApiModelProperty("投注人数")
	private Integer  betCount;
	
	@ApiModelProperty("首充人数")
	private Integer firstChargeCount;

	@ApiModelProperty("下级返点总额（金币）")
	private BigDecimal rebatesAmount;
	
	@ApiModelProperty("主播礼物总额（金币）")
	private BigDecimal giftAmount;
	
	@ApiModelProperty("订阅收入总额（金币）")
	private BigDecimal focusAmount;

	
	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

}
