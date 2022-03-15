package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @create
 **/
@Data
@ApiModel(value = "根据issue结算请求类")
public class OrderSettleByIssueReq {
    @ApiModelProperty(value = "彩种id[必填]",required = true)
    private Integer lotteryId;
    @ApiModelProperty(value = "期号[必填]",required = true)
    private String issue;
    @ApiModelProperty(value = "开奖号码[必填]",required = true)
    private String openNumber;
}

