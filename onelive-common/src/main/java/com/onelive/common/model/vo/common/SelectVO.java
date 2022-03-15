package com.onelive.common.model.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SelectVO
 * @Desc 公共下拉展示类
 * @Date 2021/4/5 19:14
 */
@Data
@ApiModel
public class SelectVO {

    @ApiModelProperty("key值")
    private Long value;

    @ApiModelProperty("描述")
    private String des;

}    
    