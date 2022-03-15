package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class shortcutOptionsUnitLang {


    @ApiModelProperty("语言标识")
    private String lang;

    @ApiModelProperty("快捷选项单位")
    private String content;
}
