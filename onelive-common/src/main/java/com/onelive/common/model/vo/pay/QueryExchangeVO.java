package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class QueryExchangeVO {

    @ApiModelProperty("参数code")
    private String paramCode;

    @ApiModelProperty("参数名称")
    private String paramName;

}
