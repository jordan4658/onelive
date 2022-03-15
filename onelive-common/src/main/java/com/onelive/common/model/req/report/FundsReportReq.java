package com.onelive.common.model.req.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName FundsReportReq
 * @Desc 资金报表请求接口
 */
@Data
@ApiModel
public class FundsReportReq {

    @ApiModelProperty("开始时间，格式yyyy-MM-dd")
    private String beginDate;
    @ApiModelProperty("结束时间，格式yyyy-MM-dd")
    private String endDate;


}    
    