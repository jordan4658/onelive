package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统时间
 */
@Data
@ApiModel
public class SysDateTimeVO {

    @ApiModelProperty("当前系统时间")
    private Long time;

}    
    