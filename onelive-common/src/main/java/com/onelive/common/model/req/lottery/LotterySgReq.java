package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种赛果资讯请求类")
public class LotterySgReq {
    @ApiModelProperty(value = "玩法id[必填]",required = true)
    private Integer id;
    @ApiModelProperty(value = "页号")
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页显示数目")
    private Integer pageSize = 10;
}
