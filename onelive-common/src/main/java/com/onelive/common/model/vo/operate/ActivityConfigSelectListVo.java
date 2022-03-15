package com.onelive.common.model.vo.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于选择的活动列表查询实体类
 */
@Data
@ApiModel
public class ActivityConfigSelectListVo {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("活动名称")
    private String activityName;
}
