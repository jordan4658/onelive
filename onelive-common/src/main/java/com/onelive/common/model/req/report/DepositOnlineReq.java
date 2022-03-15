package com.onelive.common.model.req.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName DepositOnlineReq
 * @Desc 入款线上报表请求
 * @Date 2021/4/23 9:56
 */
@Data
@ApiModel
public class DepositOnlineReq {

    @ApiModelProperty("支付商名称")
    private String providerName;
    @ApiModelProperty("开始时间，格式yyyy-MM-dd")
    private String beginDate;
    @ApiModelProperty("结束时间，格式yyyy-MM-dd")
    private String endDate;
}    
    