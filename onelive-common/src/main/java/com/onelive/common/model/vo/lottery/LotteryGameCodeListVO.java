package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种游戏code值列表返回类")
public class LotteryGameCodeListVO {

    @ApiModelProperty(value = "code值")
    private String code;

    @ApiModelProperty(value = "code值名称")
    private String name;
}
