package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统参数状态切换请求类
 * @date 2021/3/24
 */
@ApiModel
@Data
public class SysParameterSwitchReq {

    @ApiModelProperty(value = "系统参数id")
    private Long paramId;

    @ApiModelProperty(value = "系统参数启用状态0启用9未启用")
    private Integer paramStatus;
}
