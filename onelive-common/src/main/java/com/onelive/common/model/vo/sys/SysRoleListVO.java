package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统角色ListVO类
 * @date 2021/4/6
 */
@Data
@ApiModel
public class SysRoleListVO {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色状态  0正常  9停用")
    private Integer roleStatus;
}
