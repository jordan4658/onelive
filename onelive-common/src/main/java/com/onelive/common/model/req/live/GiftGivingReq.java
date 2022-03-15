package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物赠送传输类")
public class GiftGivingReq {

	@ApiModelProperty("礼物id[必填]")
	private Integer giftId;

	@ApiModelProperty("礼物数量,默认1 ")
	private Integer giftNumber = 1;

	@ApiModelProperty("接收礼物人ID [必填]")
	private Long hostId;

	@ApiModelProperty("礼物连击ID（用于判断是否连击）")
    private String giftComboId;

	@ApiModelProperty("是否礼物连击结束")
    private Boolean isComboEnd;

}
