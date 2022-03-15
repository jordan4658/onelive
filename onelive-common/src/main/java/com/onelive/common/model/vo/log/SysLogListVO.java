package com.onelive.common.model.vo.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 操作日志VO类
 */
@Data
@ApiModel
public class SysLogListVO {

    @ApiModelProperty("主键")
    private Long logId;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("日志类型")
    private String logType;

    @ApiModelProperty("ip地址")
    private String requestIp;

    @ApiModelProperty("耗时")
    private Long time;

    @ApiModelProperty("生成时间")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("操作人")
    private String username;
}
