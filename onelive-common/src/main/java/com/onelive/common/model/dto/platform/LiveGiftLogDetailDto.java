package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物记录详情,每次赠送一条记录")
public class LiveGiftLogDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主播账号")
	private String userAccountAnchor;

	@ApiModelProperty("用户登录账号")
	private String userAccount;
	
	@ApiModelProperty("房间号")
	private String studioNum;
	
	@ApiModelProperty("语言标识")
	private String lang;
	
	@ApiModelProperty(" 商品类型：1：世界礼物，2：大型礼物，3：中型礼物，4：普通礼物，5：特权礼物 6：按时收费商品 7:按场收费商品")
	private Integer giftType;
	
	@ApiModelProperty("礼物购买订单号")
	private String giftLogNo;

	@ApiModelProperty("资金流水表订单号")
	private String goldChangeNo;

	@ApiModelProperty("礼物名字")
	private String giftName;
	
	@ApiModelProperty("礼物数量")
    private Integer giftNumber;

	@ApiModelProperty("赠送时间")
	private String givingTime;
	
	@ApiModelProperty("国家Code")
	private String countryCode;

	@ApiModelProperty("礼物价格")
	private BigDecimal price;
	
	@ApiModelProperty("商户")
	private String merchantCode = "0";

	@ApiModelProperty("创建开始时间")
	private String startTime;

	@ApiModelProperty("创建结束时间")
	private String endTime;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

}
