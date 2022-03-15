package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种分类删除请求类")
public class LotteryCategoryDelReq {
    @ApiModelProperty(value = "ID[必填]",required = true)
    private Integer id;
}
