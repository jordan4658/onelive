package com.onelive.common.model.vo.pay;


import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel
public class WithdrawalOptionsVO {

    @ApiModelProperty("充值快捷选项ID")
    private Long silverBeanOptionsId;

    @ApiModelProperty("数据ID(同一条多个语言相同)")
    private String langId;

    @ApiModelProperty("多个值使用逗号分割开")
    private String optionsContent;

    @ApiModelProperty("充值选项单位（多语言）")
    private List<shortcutOptionsUnitLang> shortcutOptionsUnit;

}
