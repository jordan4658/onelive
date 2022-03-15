package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户游戏记录列表实体类
 */
@Data
@ApiModel
public class GameRecordListVO<T> implements Serializable {

    private static final long serialVersionUID=1L;


    @ApiModelProperty("下单总金额")
    private BigDecimal totalBetAmount;

    @ApiModelProperty("中奖总金额")
    private BigDecimal totalWinAmount;

    @ApiModelProperty("列表数据")
    private List<T> list;

}
