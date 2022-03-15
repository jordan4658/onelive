package com.onelive.common.model.vo.game;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 第三方游戏汇率
 * </p>
 */
@Data
@ApiModel
public class GameOutsideGamesRateListVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("游戏id")
    private Long gameId;

     @ApiModelProperty("币种")
    private String currencyCode;

     @ApiModelProperty("转入汇率")
    private BigDecimal inRate;

     @ApiModelProperty("转出汇率")
    private BigDecimal outRate;

    

}
