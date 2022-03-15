package com.onelive.common.model.vo.sys;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SysParameterListVO
 * @Desc 系统参数列表vo类
 * @Date 2021/3/24 11:01
 */
@ApiModel
@Data
public class SysParameterListVO {

    @ApiModelProperty(value = "系统参数id")
    private Long paramId;

    @ApiModelProperty(value = "系统参数名称")
    private String paramName;

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
    
    @ApiModelProperty(value = " 最后修改人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}    
    