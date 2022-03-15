package com.onelive.common.model.vo.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lorenzo
 * @Description: 系统日志VO类
 * @date 2021/4/5
 */
@Data
@ApiModel
public class SysLogVO {

    @ApiModelProperty("主键")
    private Long logId;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("日志类型")
    private String logType;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("ip地址")
    private String requestIp;

    @ApiModelProperty("参数")
    private String params;

    @ApiModelProperty("耗时")
    private Long time;

    @ApiModelProperty("生成时间")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("操作人")
    private String username;

    @ApiModelProperty("异常原因")
    private String exceptionDetail;
}
