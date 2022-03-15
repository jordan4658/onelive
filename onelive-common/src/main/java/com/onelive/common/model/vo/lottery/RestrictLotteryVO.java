package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "投注限制彩票返回类")
public class RestrictLotteryVO {

    @ApiModelProperty(value = "彩种大类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "彩票ID")
    private Integer lotteryId;

    @ApiModelProperty(value = "彩票名称")
    private String name;

}
