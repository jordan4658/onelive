package com.onelive.common.model.req.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物赠送详情请求")
public class GiftGivingReq {

	@ApiModelProperty("开始时间")
	private String startDate;

	@ApiModelProperty("结束时间")
	private String endDate;

	@ApiModelProperty("地区")
	private Integer countryId;

	@ApiModelProperty("用户账号")
	private String userAccount;

	@ApiModelProperty("赠送主播")
	private String liveName;

	@ApiModelProperty("礼物名称")
	private String giftName;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

}
