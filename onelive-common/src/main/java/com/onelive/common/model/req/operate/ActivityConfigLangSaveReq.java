package com.onelive.common.model.req.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动语种
 */
@Data
@ApiModel
public class ActivityConfigLangSaveReq {
    @ApiModelProperty("主键ID, 更新的时候传入")
    private Long id;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动标题[必填]",required = true)
    private String activityTitle;

    @ApiModelProperty(value = "活动内容[必填]",required = true)
    private String activityContent;
    /**
     * 语言
     */
    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

}
