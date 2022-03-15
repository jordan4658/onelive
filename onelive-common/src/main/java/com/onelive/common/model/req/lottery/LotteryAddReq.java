package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "添加彩种情求类")
public class LotteryAddReq {

    @ApiModelProperty(value = "彩票分类id",required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "彩种编号",required = true)
    private Integer lotteryId;

    @ApiModelProperty(value = "彩票名称",required = true)
    private String name;

    @ApiModelProperty(value = "每天/年开奖期数",required = true)
    private Integer startlottoTimes;

    @ApiModelProperty(value = "封盘倒计时(秒)",required = true)
    private Integer endTime;

    @ApiModelProperty(value = "是否开售",required = true)
    private Integer isWork;

    @ApiModelProperty(value = "排序",required = true)
    private Integer sort;

}
