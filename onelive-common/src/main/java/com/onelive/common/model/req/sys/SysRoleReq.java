package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 新增角色请求类
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysRoleReq {

    @ApiModelProperty("角色名称")
    private String roleName;

}
