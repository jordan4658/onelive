package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("APP开奖模块首页彩种ID获取信息 请求参数")
public class LotteryIdsReq {
    @ApiModelProperty(value = "ID列表, 逗号隔开[必填]",required = true)
    private String ids;
}
