package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "投注设置请求类")
public class LotteryRestrictEditReq {

    @ApiModelProperty(value = "彩种id[必填]",required = true)
    private Integer lotteryId;

    @ApiModelProperty(value = "玩法id[必填]",required = true)
    private Integer playId;

    @ApiModelProperty(value = "最大投注金额[必填]",required = true)
    private String maxMoney;

}
