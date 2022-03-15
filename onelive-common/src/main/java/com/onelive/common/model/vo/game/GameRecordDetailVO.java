package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 游戏记录详情实体类
 */
@Data
@ApiModel
public class GameRecordDetailVO implements Serializable {

    private static final long serialVersionUID=1L;


    @ApiModelProperty("游戏名称")
    private String gameName;

    @ApiModelProperty("下单金额")
    private BigDecimal betAmount;

    @ApiModelProperty("中奖金额")
    private BigDecimal winAmount;

}
