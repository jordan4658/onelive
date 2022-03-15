package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物记录传输类")
public class LiveGiftLogDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 赠送礼物时间
	 */
	private Date givingTime;

	@ApiModelProperty(" 赠送人当地时间")
	private Date givingLocalTime;

	@ApiModelProperty("商品类型：1：世界礼物，2：大型礼物，3：中型礼物，4：普通礼物，5：特权礼物 6：按时收费商品 7:按场收费商品")
	private Integer giftType;

	/**
	 * 商户code值，默认为0
	 */
	private String merchantCode = "0";

}
