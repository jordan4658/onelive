package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏下拉选项实体类")
public class GameSelectVO {
    @ApiModelProperty("游戏名称")
    private String name;

    @ApiModelProperty("选项值")
    private Integer gameId;
}
