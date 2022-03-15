package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description:
 * @date 2021/3/24
 */
@ApiModel
@Data
public class SysParameterByCodeVO {
	
    @ApiModelProperty(value = "系统参数id")
    private Long paramId;

    @ApiModelProperty(value = "系统参数名称")
    private String paramName;
    
    @ApiModelProperty(value = "系统参数类型")
    private String paramType;

    @ApiModelProperty(value = "系统参数值")
    private String paramValue;

    @ApiModelProperty(value = "系统参数代码")
    private String paramCode;

    @ApiModelProperty(value = "系统参数备注")
    private String remark;

    @ApiModelProperty(value = "排序值")
    private Integer sortBy;

    @ApiModelProperty(value = "系统参数启用状态0启用9未启用")
    private Integer paramStatus;
}
