package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lorenzo
 * @Description: 配置权限, 角色功能关系设置请求
 * @date 2021/3/31
 */
@Data
@ApiModel
public class RoleFunctionReq {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("功能id列表")
    private List<Long> funIdList;
}
