package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "用户投注信息返回类")
public class OrderBetRecordDetailVO {
    @ApiModelProperty(value = "投注记录ID")
    private Integer id;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "会员id")
    private Long userId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "用户账号")
    private String accno;
    @ApiModelProperty(value = "期号")
    private String issue;
    @ApiModelProperty(value = "玩法")
    private String lotteryPlay;
    @ApiModelProperty(value = "彩种")
    private String lotteryName;
    @ApiModelProperty(value = "国家地区")
    private String countryName;
    @ApiModelProperty(value = "投注内容")
    private String betNumber;
    @ApiModelProperty(value = "投注额")
    private BigDecimal betAmount;
    @ApiModelProperty(value = "赔率")
    private String odds;
    @ApiModelProperty(value = "返点")
    private BigDecimal backAmount;

}
