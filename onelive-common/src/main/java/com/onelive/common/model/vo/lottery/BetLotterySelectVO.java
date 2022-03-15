package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "彩种下拉-玩法下拉返回类")
public class BetLotterySelectVO {

    @ApiModelProperty(value = "彩种ID")
    private Integer lotteryId;

    @ApiModelProperty(value = "彩种名称")
    private String name;

    @ApiModelProperty(value = "玩法列表")
    private List<BetLotterySelectChildVO> childList = new ArrayList<>();

}
