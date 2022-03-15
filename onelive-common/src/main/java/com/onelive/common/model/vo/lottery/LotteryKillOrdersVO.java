package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "杀号配置列表返回类")
public class LotteryKillOrdersVO {

    @ApiModelProperty(value = "赔率ID")
    private Integer id;
    @ApiModelProperty(value = "彩种名称")
    private String name;
    @ApiModelProperty(value = "彩种id")
    private Integer lotteryId;
    @ApiModelProperty(value = "平台标识")
    private String platfom;
    @ApiModelProperty(value = "杀号比例")
    private String ratio;






}
