package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "检查游戏是否可以跳转")
public class LotteryGameCheckJumpVO {

    @ApiModelProperty(value = "true可以跳转，false不可以跳转")
    private Boolean value;

}
