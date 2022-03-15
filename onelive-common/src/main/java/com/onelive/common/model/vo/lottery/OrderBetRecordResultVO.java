package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "彩票详情返回类")
public class OrderBetRecordResultVO {

        @ApiModelProperty(value = "投注id")
        private Integer betId;
        @ApiModelProperty(value = "期号")
        private String issue;
        @ApiModelProperty(value = "订单号")
        private String orderSn;
        @ApiModelProperty(value = "彩种类别id")
        private Integer cateId;
        @ApiModelProperty(value = "彩种id")
        private Integer lotteryId;
        @ApiModelProperty(value = "玩法id")
        private Integer playId;
        @ApiModelProperty(value = "玩法配置id")
        private Integer settingId;
        @ApiModelProperty(value = "玩法名称")
        private String playName;
        @ApiModelProperty(value = "投注号码")
        private String betNumber;
        @ApiModelProperty(value = "投注总注数")
        private Integer betCount;
        @ApiModelProperty(value = "投注金额")
        private BigDecimal betAmount;
        @ApiModelProperty(value = "中奖金额")
        private BigDecimal winAmount;
        @ApiModelProperty(value = "返点金额")
        private BigDecimal backAmount;
        @ApiModelProperty(value = "中奖:WIN | 未中奖:NO_WIN | 等待开奖:WAIT | 和:HE | 撤单:BACK")
        private String tbStatus;
        @ApiModelProperty(value = "中奖注数")
        private String winCount;
        @ApiModelProperty(value = "是否推单 0 否 1 是")
        private Integer isPush;
        @ApiModelProperty(value = "直播房间id")
        private Long studioId;

}
