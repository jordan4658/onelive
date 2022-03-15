package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@ApiModel(value = "投注列表实体类")
public class OrderBetVO {

//    @ApiModelProperty(value = "投注日期")
//    private Date date;
//    @ApiModelProperty(value = "投注类型：NORMAL 投注 | BACK 撤单")
//    private String type;
//    @ApiModelProperty(value = "状态：中奖:WIN | 未中奖:NO_WIN | 等待开奖:WAIT | 和:HE | 撤单:BACK")
//    private String status;
//    @ApiModelProperty(value = "彩种id集合")
//    private List<Integer> lotteryIds;
//    @ApiModelProperty(value = "排序字段名称：bet_amount:投注金额 | win_amount:中奖金额 | create_time:投注时间")
//    private String sortName;
//    @ApiModelProperty(value = "排序方式：ASC:顺序 | DESC:倒序")
//    private String sortType;
    @ApiModelProperty(value = "投注id")
    private Integer id;
    @ApiModelProperty(value = "期号")
    private String issue;
    @ApiModelProperty(value = "彩种名称")
    private String lotteryName;
    @ApiModelProperty(value = "玩法名称")
    private String playName;
    @ApiModelProperty(value = "赔率")
    private String odds;
    @ApiModelProperty(value = "订单id")
    private Integer orderId;
    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @ApiModelProperty(value = "彩种id")
    private Integer lotteryId;
    @ApiModelProperty(value = "玩法id")
    private Integer playId;
    @ApiModelProperty(value = "玩法配置id")
    private Integer settingId;
    @ApiModelProperty(value = "投注号码")
    private String betNumber;
    @ApiModelProperty(value = "开奖号码")
    private String openNumber;
    @ApiModelProperty(value = "投注注数")
    private Integer betCount;
    @ApiModelProperty(value = "投注总额")
    private BigDecimal betAmount;
    @ApiModelProperty(value = "投注总额，用于投注总额")
    private String betAmountIos;
    @ApiModelProperty(value = "中奖金额")
    private BigDecimal winAmount;
    @ApiModelProperty(value = "返点金额")
    private BigDecimal backAmount;
    @ApiModelProperty(value = "状态：中奖:WIN | 未中奖:NO_WIN | 等待开奖:WAIT | 和:HE | 撤单:BACK")
    private String tbStatus;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "中奖注数")
    private String winCount;
}
