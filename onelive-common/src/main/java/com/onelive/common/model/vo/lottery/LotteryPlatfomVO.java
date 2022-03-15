package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "平台列表返回类")
public class LotteryPlatfomVO {

    @ApiModelProperty(value = "彩种名称")
    private String name;

    @ApiModelProperty(value = "平台标识")
    private String platfom;

}
