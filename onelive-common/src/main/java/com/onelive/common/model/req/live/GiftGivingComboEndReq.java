package com.onelive.common.model.req.live;


import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
@Validated
public class GiftGivingComboEndReq {

    @ApiModelProperty("礼物id [必填]")
    private Integer giftId;

    @ApiModelProperty("直播间Num [必填]")
    private String studioNum;

    @ApiModelProperty("接收礼物人ID [必填]")
    private Long hostId;

    @ApiModelProperty("礼物连击ID（用于判断是否连击） [必填]")
    private String giftComboId;

}
