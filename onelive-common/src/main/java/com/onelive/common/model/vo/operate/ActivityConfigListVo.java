package com.onelive.common.model.vo.operate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 活动管理列表查询实体类
 */
@Data
@ApiModel
public class ActivityConfigListVo {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动类型")
    private Integer activityType;

    @ApiModelProperty("活动状态")
    private Boolean isActive;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
