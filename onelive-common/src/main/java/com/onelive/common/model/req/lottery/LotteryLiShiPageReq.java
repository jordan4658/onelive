package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩票历史记录请求类")
public class LotteryLiShiPageReq {
    @ApiModelProperty("页码")
    private Integer pageNo;
    @ApiModelProperty("数量")
    private Integer pageSize;
    @ApiModelProperty("ID")
    private Integer id;
}
