package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统用户与角色关系新增
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysUserRoleReq {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("会员id")
    private Long userId;

}
