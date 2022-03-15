package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统角色查询参数请求
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysRoleQueryReq {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;

}
