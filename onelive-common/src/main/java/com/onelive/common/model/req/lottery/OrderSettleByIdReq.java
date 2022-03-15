package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @create
 **/
@Data
@ApiModel(value = "通过注单id进行结算请求类")
public class OrderSettleByIdReq {
    @ApiModelProperty(value = "注单id[必填]",required = true)
    private Integer id;
    @ApiModelProperty(value = "中奖金额[必填]",required = true)
    private String winAmount;
    @ApiModelProperty(value = "中奖状态[必填]",required = true)
    private String tbStatus;
    @ApiModelProperty(value = "开奖号码[必填]",required = true)
    private String openNumber;
}

