package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName BargainingChipConfigReq
 * @Desc 平台配置中的筹码配置请求参数
 */
@ApiModel
@Data
public class SysBargainingChipConfigListReq {

    @ApiModelProperty(value = "筹码配置列表[必填]",required = true)
    List<SysBargainingChipConfigReq> list;
}    
    