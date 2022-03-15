package com.onelive.common.model.dto.platform;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物赠送传输类")
public class GiftGivingDto {

	@ApiModelProperty("礼物id")
	private Integer giftId;

	@ApiModelProperty("礼物数量,默认1")
	private Integer giftNumber = 1;

	@ApiModelProperty("直播间Num")
	private String studioNum;

	@ApiModelProperty("接收礼物人ID")
	private Long hostId;

	@ApiModelProperty("礼物连击ID（用于判断是否连击）")
    private String giftComboId;
	
	@ApiModelProperty("用户余额")
	private BigDecimal balance;
	
	public GiftGivingDto() {
		super();
	}
	
	public GiftGivingDto(String giftComboId, BigDecimal balance) {
		super();
		this.giftComboId = giftComboId;
		this.balance = balance;
	}

}
