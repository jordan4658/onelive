package com.onelive.common.model.vo.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家族长财务对账")
public class FamilyFinancialRecord {

    @ApiModelProperty("账变类型 1：转入 2： 提出 3：其他 ")
    private Integer type;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("创建时间 ")
    private String createTime;

}
