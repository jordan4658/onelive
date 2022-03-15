package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统角色删除请求类
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysRoleDeleteReq {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色状态  0正常  9停用")
    private Integer roleStatus;
}
