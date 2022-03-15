package com.onelive.common.model.vo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SysAdvActivityLangVO {
    /**
     * 主键
     */
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 活动名称
     */
    @ApiModelProperty("活动名称")
    private String activityName;

    /**
     * 活动内容
     */
    @ApiModelProperty("活动内容")
    private String activityContent;

    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String lang;


}
