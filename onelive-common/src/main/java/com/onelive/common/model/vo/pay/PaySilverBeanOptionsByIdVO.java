package com.onelive.common.model.vo.pay;

import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PaySilverBeanOptionsByIdVO {

    @ApiModelProperty("充值快捷选项ID")
    private Long silverBeanOptionsId;

    @ApiModelProperty("多个值使用逗号分割开")
    private String optionsContent;

}
