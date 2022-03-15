package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种赔率列表返回类")
public class LotteryPlayOddsListVO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "赔率选项名称")
    private String name;

    @ApiModelProperty(value = "总注数")
    private String totalCount;

    @ApiModelProperty(value = "中奖注数")
    private String winCount;

}
