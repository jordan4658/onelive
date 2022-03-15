package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 业务参数修改状态请求类
 * @date 2021/4/6
 */
@Data
@ApiModel
public class SysBusParameterStatusReq {

    @ApiModelProperty("业务参数主键")
    private Long id;

    @ApiModelProperty("系统参数启用状态 0启用 9未启用")
    private Integer status;

}
