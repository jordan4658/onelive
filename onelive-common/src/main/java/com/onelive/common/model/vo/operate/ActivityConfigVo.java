package com.onelive.common.model.vo.operate;

import com.onelive.common.model.req.operate.ActivityConfigLangSaveReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ActivityConfigVo {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动类型")
    private Integer activityType;

    @ApiModelProperty("活动状态")
    private Boolean isActive;

    @ApiModelProperty("跳转地址")
    private String skipUrl;

    @ApiModelProperty("活动配置内容")
    private Object config;

    @ApiModelProperty("多语言列表")
    private List<ActivityConfigLangVO> langList;
}
