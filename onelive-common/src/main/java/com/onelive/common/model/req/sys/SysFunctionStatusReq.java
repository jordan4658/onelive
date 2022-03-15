package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 功能模块切换状态请求
 * @date 2021/4/7
 */
@Data
@ApiModel
public class SysFunctionStatusReq {

    @ApiModelProperty("功能id")
    private Long funcId;

    @ApiModelProperty("模块状态 0正常   9停用")
    private Integer funcStatus;
}
