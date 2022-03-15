package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("活动列表用于选择的内容")
public class SysAdvActivitySelectVO {

    @ApiModelProperty("活动ID")
    private Long id;

    /**
     * 活动名称
     */
    @ApiModelProperty("活动名称")
    private String activityName;
}
