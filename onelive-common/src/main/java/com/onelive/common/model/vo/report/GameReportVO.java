package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("游戏报表实体类")
public class GameReportVO {

    @ApiModelProperty("游戏平台名称")
    private String name;

    @ApiModelProperty("游戏平台code, 彩票:Lottery, OBG:OBG,..")
    private String code;

    @ApiModelProperty("总下注金额")
    private BigDecimal betAmount;

    @ApiModelProperty("总中奖金额")
    private BigDecimal winAmount;

    @ApiModelProperty("用户数量")
    private Integer userCount;

    @ApiModelProperty("下注总数量")
    private Integer betCount;

    @ApiModelProperty("用户盈亏金额")
    private BigDecimal userWinAmount;

    @ApiModelProperty("平台盈亏金额")
    private BigDecimal platformWinAmount;

    @ApiModelProperty("盈率")
    private BigDecimal ratio;

}
