package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播游戏请求类")
public class LotteryCountryReq {

    @ApiModelProperty(value = "国家id[必填]",required = true)
    private Long countryId;

}
