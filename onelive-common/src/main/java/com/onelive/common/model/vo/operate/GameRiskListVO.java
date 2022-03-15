package com.onelive.common.model.vo.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 游戏风控列表信息实体类
 */
@Data
@ApiModel("游戏风控列表信息实体类")
public class GameRiskListVO {

    //彩种
    @ApiModelProperty("彩种名称")
    private String name;

    //期号
    @ApiModelProperty("期号")
    private String issue;

    //投注人数
    @ApiModelProperty("投注人数")
    private Integer betUserCount;

    //中奖人数
    @ApiModelProperty("中奖人数")
    private Integer winUserCount;

    //注单数
    @ApiModelProperty("注单数")
    private Integer betCount;

    //中奖注单数
    @ApiModelProperty("中奖注单数")
    private Integer winCount;

    //下单总额
    @ApiModelProperty("下单总额")
    private BigDecimal betAmount;

    //中奖总额
    @ApiModelProperty("中奖总额")
    private BigDecimal winAmount;

}
