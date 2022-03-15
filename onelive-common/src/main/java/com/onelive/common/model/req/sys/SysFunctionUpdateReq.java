package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 功能模块修改请求
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysFunctionUpdateReq {

    @ApiModelProperty("功能id")
    private Long funcId;

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

    @ApiModelProperty("模块状态 0正常   9停用")
    private Integer funcStatus;
}
