package com.onelive.common.model.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("注单信息实体类")
public class LotteryOrderBetRecordVO {

    /**
     * 投注单id
     */
    @ApiModelProperty("订单ID")
    private Integer orderId;

    /**
     * 彩种类别id
     */
    @ApiModelProperty("彩种类别id")
    private Integer cateId;

    /**
     * 彩种id
     */
    @ApiModelProperty("彩种id")
    private Integer lotteryId;

    /**
     * 玩法id
     */
    @ApiModelProperty("玩法id")
    private Integer playId;

    /**
     * 玩法配置id
     */
    @ApiModelProperty("玩法配置id")
    private Integer settingId;

    /**
     * 玩法名称
     */
    @ApiModelProperty("玩法名称")
    private String playName;

    /**
     * 购买的期号
     */
    @ApiModelProperty("购买的期号")
    private String issue;

    /**
     * 投注号码
     */
    @ApiModelProperty("投注号码")
    private String betNumber;

    /**
     * 投注总注数
     */
    @ApiModelProperty("投注总注数")
    private Integer betCount;

    /**
     * 投注金额
     */
    @ApiModelProperty("投注金额")
    private BigDecimal betAmount;

}
