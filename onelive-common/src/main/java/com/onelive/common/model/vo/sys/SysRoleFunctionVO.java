package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lorenzo
 * @Description: 系统角色模块权限VO
 * @date 2021/4/6
 */
@Data
@ApiModel
public class SysRoleFunctionVO {

    @ApiModelProperty("是否选中")
    private Integer checkbox;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("功能模块id")
    private Long funcId;

    @ApiModelProperty("父级功能模块id")
    private Long parentFuncId;

    @ApiModelProperty("模块名称")
    private String funcName;

    @ApiModelProperty("功能模块类型")
    private String funcType;

    @ApiModelProperty("模块参数")
    private String funcUrl;

    @ApiModelProperty("子项")
    private List<SysRoleFunctionVO> children;
}
