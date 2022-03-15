package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统用户修改状态请求
 * @date 2021/3/31
 */
@Data
@ApiModel
public class SysUserStatusReq {

    @ApiModelProperty("系统用户id")
    private Long userId;

    @ApiModelProperty("状态 1启用 9禁用")
    private Integer status;

}
