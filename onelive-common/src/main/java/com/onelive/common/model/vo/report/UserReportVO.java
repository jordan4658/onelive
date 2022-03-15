package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName UserReportVO
 * @Desc 会员报表展示类
 * @Date 2021/4/13 16:14
 */
@Data
@ApiModel
public class UserReportVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("会员等级")
    private Integer memLevel;

    @ApiModelProperty("会员状态 false正常 true异常")
    private Boolean freezeStatus;

    @ApiModelProperty("用户金币余额 前端保留2位小数")
    private BigDecimal amount;

    @ApiModelProperty("用户银豆余额 前端保留2位小数")
    private BigDecimal silverAmount;

    @ApiModelProperty("总充值金额")
    private BigDecimal sumRechargeAmount;

    @ApiModelProperty("总出款金额")
    private BigDecimal sumWithdrawAmount;

    @ApiModelProperty("总打码量")
    private BigDecimal sumAccountDml;

    @ApiModelProperty("剩余打码量")
    private BigDecimal accountDml;

    @ApiModelProperty("提现次数")
    private BigDecimal withdrawalNum;

    @ApiModelProperty("充值总次数")
    private BigDecimal payNum;

    @ApiModelProperty("最大提现金额")
    private BigDecimal withdrawalMax;

    @ApiModelProperty("最大充值金额")
    private BigDecimal payMax;





}    
    