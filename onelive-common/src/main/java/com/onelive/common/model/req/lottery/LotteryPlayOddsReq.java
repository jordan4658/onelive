package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩票玩法赔率列表请求类")
public class LotteryPlayOddsReq {
    @ApiModelProperty(value = "规则id[必填]",required = true)
    private Integer settingId;
}
