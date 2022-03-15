package com.onelive.common.model.req.operate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 红包活动配置参数
 */
@Data
@ApiModel
public class RedPacketActivityConfigSaveReq extends ActivityConfigSaveReq {

    @ApiModelProperty(value = "维护状态,true正常,false维护中[必填]",required = true)
    private Boolean maintainStatus;

    @ApiModelProperty("是否固定时间")
    private Boolean isFixedTime;
    @ApiModelProperty("固定时间 开始时间")
    @JsonFormat(pattern="HH:mm:ss")
    private Date fixedTimeStartTime;
    @ApiModelProperty("固定时间 开始时间")
    @JsonFormat(pattern="HH:mm:ss")
    private Date fixedTimeEndTime;
    @ApiModelProperty("固定时间 领奖周期(分钟)")
    private Integer fixedTimeCycle;
    @ApiModelProperty("固定时间 下次间隔(分钟)")
    private Integer fixedTimeNext;

    @ApiModelProperty("是否投注金额")
    private Boolean isBetAmount;
    @ApiModelProperty("投注金额 开始时间")
    @JsonFormat(pattern="HH:mm:ss")
    private Date betAmountStartTime;
    @ApiModelProperty("投注金额 开始时间")
    @JsonFormat(pattern="HH:mm:ss")
    private Date betAmountEndTime;
    @ApiModelProperty("投注金额 领奖周期(分钟)")
    private Integer betAmountCycle;
    @ApiModelProperty("投注金额 下次间隔(分钟)")
    private Integer betAmountNext;


    @ApiModelProperty("是否礼物金额")
    private Boolean isGiftsAmount;
    @ApiModelProperty("礼物金额 开始时间")
    @JsonFormat(pattern="HH:mm:ss")
    private Date giftsAmountStartTime;
    @ApiModelProperty("礼物金额 开始时间")
    @JsonFormat(pattern="HH:mm:ss")
    private Date giftsAmountEndTime;
    @ApiModelProperty("礼物金额 领奖周期(分钟)")
    private Integer giftsAmountCycle;
    @ApiModelProperty("礼物金额 下次间隔(分钟)")
    private Integer giftsAmountNext;

    @ApiModelProperty("每场次红包个数")
    private Integer redPacketCount;

    @ApiModelProperty("打码倍率")
    private BigDecimal ratio;

    @ApiModelProperty("不中的层级(数组)")
    private List<Integer> notAllowGroup;

    @ApiModelProperty("不中VIP等级(数组) 存level_weight")
    private List<Integer> notAllowVip;


    @ApiModelProperty("是否随机")
    private Boolean isRandom;
    @ApiModelProperty("是否指定")
    private Boolean isAppoint;

    @ApiModelProperty("昨日消费大于")
    private Boolean isYesterdayConsume;
    @ApiModelProperty("昨日充值大于")
    private Boolean isYesterdayRecharge;
    @ApiModelProperty("昨日礼物大于")
    private Boolean isYesterdayGifts;

    @ApiModelProperty("今日消费大于")
    private Boolean isTodayConsume;
    @ApiModelProperty("今日充值大于")
    private Boolean isTodayRecharge;
    @ApiModelProperty("今日礼物大于")
    private Boolean isTodayGifts;

    @ApiModelProperty("用户最低领取等级 存level_weight")
    private Integer minVip;

    @ApiModelProperty("单期用户限领一次")
    private Boolean isSingle;

    @ApiModelProperty("主播黑名单(数组)")
    private List<String> anchorBlackList;


    @ApiModelProperty("投注达标金额")
    private BigDecimal betAmount;
    @ApiModelProperty("礼物达标金额")
    private BigDecimal giftsAmount;


    @ApiModelProperty("红包区间1") // 精确到小数点后二位
    private BigDecimal percent1;
    @ApiModelProperty("红包区间1 最小领取金额")
    private BigDecimal percent1MinAmount;
    @ApiModelProperty("红包区间1 最大领取金额")
    private BigDecimal percent1MaxAmount;
    @ApiModelProperty("红包区间2") // 精确到小数点后二位
    private BigDecimal percent2;
    @ApiModelProperty("红包区间2 最小领取金额")
    private BigDecimal percent2MinAmount;
    @ApiModelProperty("红包区间2 最大领取金额")
    private BigDecimal percent2MaxAmount;


    @ApiModelProperty("昨日有打码量 大于")
    private BigDecimal yesterdayConsume;
    @ApiModelProperty("昨日有充值 大于")
    private BigDecimal yesterdayRecharge;
    @ApiModelProperty("昨日有送礼 大于")
    private BigDecimal yesterdayGifts;

    @ApiModelProperty("今日有打码量 大于")
    private BigDecimal todayConsume;
    @ApiModelProperty("今日有充值 大于")
    private BigDecimal todayRecharge;
    @ApiModelProperty("今日有送礼 大于")
    private BigDecimal todayGifts;

    @ApiModelProperty("本次活动上限金额")
    private BigDecimal amountLimit;

}
