package com.onelive.common.model.req.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lorenzo
 * @Description: 日志查询条件
 * @date 2021/4/2
 */
@Data
@ApiModel
public class LogQueryParam {

    @ApiModelProperty("操作人")
    private String username;

    @ApiModelProperty("日志类型")
    private String logType;

    @ApiModelProperty("内容描述")
    private String description;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;
}
