package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 业务参数req类
 * @date 2021/4/6
 */
@Data
@ApiModel
public class SysBusParameterReq {

    @ApiModelProperty("业务参数代码")
    private String paramCode;

    @ApiModelProperty("业务名称")
    private String paramName;

    @ApiModelProperty("业务参数父代码")
    private String pParamCode;

    @ApiModelProperty("业务参数值")
    private String paramValue;

    @ApiModelProperty("参数说明")
    private String remark;

    @ApiModelProperty("系统参数启用状态 0启用 9未启用")
    private Integer status;

}
