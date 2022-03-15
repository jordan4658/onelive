package com.onelive.common.model.vo.pay;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class SilverBeanOptionsVO {

    @ApiModelProperty("银豆兑换快捷选项ID")
    private Long silverBeanOptionsId;

    @ApiModelProperty("银豆数量")
    private String option;

    @ApiModelProperty("金币数量")
    private BigDecimal platformGoldNum;

    @ApiModelProperty("银豆单位")
    private String optionsUnit;

    @ApiModelProperty("金币单位")
    private String platformGoldUint;

    @ApiModelProperty("兑换汇率")
    private String exChange;

}
