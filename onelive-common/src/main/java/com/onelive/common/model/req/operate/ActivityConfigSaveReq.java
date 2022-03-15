package com.onelive.common.model.req.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动配置保存请求参数
 */
@Data
@ApiModel
public class ActivityConfigSaveReq {

    @ApiModelProperty("主键ID")
    private Long id;

//    @ApiModelProperty("活动名称")
//    private String activityName;

    @ApiModelProperty(value = "活动类型[必填]",required = true)
    private Integer activityType;

    @ApiModelProperty(value = "活动状态[必填]",required = true)
    private Boolean isActive;

    @ApiModelProperty(value = "跳转地址[必填]",required = true)
    private String skipUrl;

    @ApiModelProperty(value = "多语言列表[必填]",required = true)
    private List<ActivityConfigLangSaveReq> langList;

}
