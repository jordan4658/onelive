package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "杀号配置请求类")
public class LotteryKillOrdersReq {

    @ApiModelProperty(value = "赔率ID[必填]",required = true)
    private Integer id;
    @ApiModelProperty(value = "平台标识[必填]",required = true)
    private String platfom;
    @ApiModelProperty(value = "杀号比例[必填]",required = true)
    private String ratio;






}
