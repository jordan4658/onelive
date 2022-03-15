package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SysLangListVO {



    @ApiModelProperty(value = "国家中文名称")
    private String zhName;

    @ApiModelProperty(value = "语言中文名称")
    private String langZh;

    @ApiModelProperty(value = "语言") 
    private String lang;

}
