package com.onelive.common.model.vo.lottery;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 用户投注返回类
 * @create
 **/
@Data
@ApiModel(value = "用户投注返回类")
public class MemberBetVO {

    @ApiModelProperty(value = "投注记录ID")
    private Integer id;

    @ApiModelProperty(value = "会员id")
    private Long userId;

    @ApiModelProperty(value = "投注时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date betTime;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户账号")
    private String accno;

    @ApiModelProperty(value = "呢称")
    private String nickName;

    @ApiModelProperty(value = "国家地区")
    private String countryName;

    @ApiModelProperty(value = "彩种id")
    private Integer lotteryId;

    @ApiModelProperty(value = "彩种")
    private String lotteryName;

    @ApiModelProperty(value = "期号")
    private String issue;

    @ApiModelProperty(value = "玩法id")
    private Integer pid;
    //private Integer lotteryPlayId;

    @ApiModelProperty(value = "玩法")
    private String lotteryPlay;

    @ApiModelProperty(value = "开奖号码")
    private String openNumber;

    @ApiModelProperty(value = "投注内容")
    private String betNumber;

    @ApiModelProperty(value = "投注额")
    private BigDecimal betAmount;

    @ApiModelProperty(value = "注单状态")
    private String tbStatus;

    @ApiModelProperty(value = "中奖额")
    private BigDecimal winAmount;

    @ApiModelProperty(value = "用户盈亏")
    private BigDecimal changeMoney;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}

