package com.onelive.common.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 游戏报表下拉选项
 */
@Getter
@AllArgsConstructor
public enum GameReportColumnEnums {

    USER_COUNT("投注人数","userCount"),
    BET_COUNT("投注量","betCount"),
    BET_AMOUNT("总投注金额","betAmount"),
    WIN_AMOUNT("中奖金额","winAmount"),
    PLATFORM_WIN_AMOUNT("平台盈亏","platformWinAmount"),
    USER_WIN_AMOUNT("用户盈亏","userWinAmount"),
    RATIO("盈率","ratio");

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("选项值")
    private String column;

}
