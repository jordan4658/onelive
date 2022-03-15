package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lorenzo
 * @Description: 功能VO类
 * @date 2021/4/1
 */
@Data
@ApiModel
public class SysFunctionVO {

    @ApiModelProperty("子项")
    private List<SysFunctionVO> children;

    @ApiModelProperty("功能id")
    private Long funcId;

    @ApiModelProperty("父级id")
    private Long parentFuncId;

    @ApiModelProperty("功能名称")
    private String funcName;

    @ApiModelProperty("功能类别  menu菜单   button按钮   tabTAB")
    private String funcType;

    @ApiModelProperty("功能url或参数")
    private String funcUrl;

    @ApiModelProperty("模块状态 0正常   9停用")
    private Integer funcStatus;

}
