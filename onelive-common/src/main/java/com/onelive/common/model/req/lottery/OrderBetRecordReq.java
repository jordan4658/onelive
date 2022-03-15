package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("投注下注请求信息")
public class OrderBetRecordReq {

    @ApiModelProperty(value = "玩法名称[必填]", required = true)
    private String playName;
    @ApiModelProperty(value = "投注号码[必填]", required = true)
    private String betNumber;
    @ApiModelProperty(value = "玩法配置id[必填]", required = true)
    private Integer settingId;
    @ApiModelProperty(value = "玩法play_tag_id[必填]", required = true)
    private Integer playId;
    @ApiModelProperty(value = "投注总注数[必填]", required = true)
    private Integer betCount;
    @ApiModelProperty(value = "投注金额[必填]", required = true)
    private BigDecimal betAmount;
    @ApiModelProperty(value = "彩种id[必填]", required = true)
    private Long lotteryId;


    @ApiModelProperty(value = "彩票名称(显示)[必填]", required = true)
    private String showLotteryName;
    @ApiModelProperty(value = "玩法名称(显示)[必填]", required = true)
    private String showPlayName;
    @ApiModelProperty(value = "投注号码(显示)[必填]", required = true)
    private String showBetNumber;
    @ApiModelProperty(value = "玩法id[必填]", required = true)
    private Integer pid;
}
