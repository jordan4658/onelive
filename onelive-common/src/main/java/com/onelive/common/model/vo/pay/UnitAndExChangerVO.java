package com.onelive.common.model.vo.pay;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UnitAndExChangerVO {

    @ApiModelProperty("充值币种单位")
    private String  unit;

    @ApiModelProperty("充值汇率")
    private String reExChanger;
}
