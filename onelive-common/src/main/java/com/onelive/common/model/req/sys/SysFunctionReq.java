package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 模块新增请求
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysFunctionReq {

    @ApiModelProperty("功能名称")
    private String funcName;

    @ApiModelProperty("所属系统")
    private String ofSystem;

    @ApiModelProperty("功能类别  menu菜单   button按钮   tabTAB")
    private String funcType;

    @ApiModelProperty("功能url或参数")
    private String funcUrl;

    @ApiModelProperty("父功能id")
    private Long parentFuncId;
}
