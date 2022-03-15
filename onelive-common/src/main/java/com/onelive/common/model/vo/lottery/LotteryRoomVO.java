package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "房间游戏返回类")
public class LotteryRoomVO {

    @ApiModelProperty(value = "游戏id")
    private Integer id;
    @ApiModelProperty(value = "图案地址")
    private String icon;
    @ApiModelProperty(value = "游戏名称")
    private String name;

}
