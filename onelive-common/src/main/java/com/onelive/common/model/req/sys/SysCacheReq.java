package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SysCacheReq
 * @Desc 缓存查询实体类
 * @Date 2021/4/6 11:48
 */
@Data
@ApiModel
public class SysCacheReq {
    @ApiModelProperty("缓存key值")
    private String key;
    @ApiModelProperty("缓存类型")
    private String type;
    @ApiModelProperty("缓存的field值")
    private String field;
    @ApiModelProperty("开始索引")
    private Long start;
    @ApiModelProperty("结束索引")
    private Long end;
}    
    