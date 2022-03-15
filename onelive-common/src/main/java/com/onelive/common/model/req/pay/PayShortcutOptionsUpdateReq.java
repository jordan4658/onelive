package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class PayShortcutOptionsUpdateReq  extends  PayShortcutOptionsAddReq{

    @ApiModelProperty("快捷选项ID")
    private Long shortcutOptionsId;

}
