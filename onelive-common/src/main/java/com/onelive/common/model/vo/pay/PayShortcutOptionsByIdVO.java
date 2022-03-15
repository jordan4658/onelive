package com.onelive.common.model.vo.pay;


import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PayShortcutOptionsByIdVO {

    @ApiModelProperty("充值快捷选项ID")
    private Long shortcutOptionsId;

    @ApiModelProperty("支付方式ID")
    private Long payWayId;

    @ApiModelProperty("国家编码")
    private String countryCode;

    @ApiModelProperty("多个值使用逗号分割开")
    private String shortcutOptionsContent;


}
