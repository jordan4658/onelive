package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PayShortcutOptionsDeleteReq {

    @ApiModelProperty("快捷选项ID")
    private List<Long> shortcutOptionsIds;
}
