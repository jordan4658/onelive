package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "投注限制玩法列表返回类")
public class RestrictLotteryPlayVO {

    @ApiModelProperty(value = "彩票玩法名称")
    private String name;

    @ApiModelProperty(value = "彩票玩法ID")
    private Integer playId;

    @ApiModelProperty(value = "彩票ID")
    private Integer lotteryId;

    @ApiModelProperty(value = "投注限制金额")
    private BigDecimal maxMoney;


}
