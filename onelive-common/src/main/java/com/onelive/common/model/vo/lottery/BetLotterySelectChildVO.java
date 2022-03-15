package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "玩法列表下拉返回类")
public class BetLotterySelectChildVO {


    @ApiModelProperty(value = "彩票ID")
    private Integer lotteryId;

    @ApiModelProperty(value = "玩法ID")
    private Integer playId;

    @ApiModelProperty(value = "玩法名称")
    private String name;

}
