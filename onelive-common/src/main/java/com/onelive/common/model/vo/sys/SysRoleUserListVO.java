package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lorenzo
 * @Description: 角色所属的用户列表VO
 * @date 2021/4/7
 */
@Data
@ApiModel
public class SysRoleUserListVO {

    @ApiModelProperty("包含在内的")
    private List<SysUserRoleVO> on = new ArrayList<>();

    @ApiModelProperty("排除在外的")
    private List<SysUserRoleVO> out = new ArrayList<>();

}
