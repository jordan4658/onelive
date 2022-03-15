package com.onelive.common.model.req.report;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏详情报表请求参数")
public class GameDetailReportReq extends PageReq {

    @ApiModelProperty("游戏平台code")
    private String code;

    @ApiModelProperty("游戏ID")
    private Integer gameId;

    @ApiModelProperty("项目")
    private String column;

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;


}
