package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SysCacheReq
 * @Desc 删除指定类型缓存请求类
 * @Date 2021/4/6 11:48
 */
@Data
@ApiModel
public class SysCacheTypeReq {
    @ApiModelProperty("缓存类型")
    private String type;
}    
    