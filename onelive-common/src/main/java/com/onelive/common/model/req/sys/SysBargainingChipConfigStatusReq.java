package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 切换配置状态请求参数
 */
@ApiModel
@Data
public class SysBargainingChipConfigStatusReq {
    @ApiModelProperty(value = "配置项id[必填]",required = true)
    private Long id;

    @ApiModelProperty(value = "是否启用 false否 true是[必填]",required = true)
    private Boolean status;

}
