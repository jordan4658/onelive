package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lorenzo
 * @Description: 系统角色VO类
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysRoleDetailVO {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("系统功能列表")
    private List<SysRoleFunctionVO> functionList;
}
