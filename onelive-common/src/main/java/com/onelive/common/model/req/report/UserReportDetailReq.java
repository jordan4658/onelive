package com.onelive.common.model.req.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName UserReportDetailReq
 * @Desc 用户报表明细请求接口
 */
@Data
@ApiModel
public class UserReportDetailReq {

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("开始时间")
    private String beginDate;

    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("帐变类型，以逗号分隔")
    private String changeTypes;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;

}    
    