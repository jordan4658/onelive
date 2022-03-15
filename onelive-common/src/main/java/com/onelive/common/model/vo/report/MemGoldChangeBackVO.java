package com.onelive.common.model.vo.report;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("统计-账变记录列表")
public class MemGoldChangeBackVO {

    @ApiModelProperty("账户变动明细id")
    private Long goldChangId;

    @ApiModelProperty("金额变动类型：1=银豆、2=金币")
    private Long goldType;

    @ApiModelProperty("用户名称")
    private String account;

    @ApiModelProperty("相关单号")
    private String refNo;

    @ApiModelProperty("会员账号")
    private String nickName;

    @ApiModelProperty("变动类型 ")
    private Integer changeType;

    @ApiModelProperty("账变金币")
    private BigDecimal amount;

    @ApiModelProperty("账变银豆")
    private BigDecimal silverAmount;

    @ApiModelProperty("变前-钱包金币余额")
    private BigDecimal goldNum;

    @ApiModelProperty("变前-钱包银豆余额")
    private BigDecimal silverNum;

    @ApiModelProperty("变后-钱包银豆余额")
    private BigDecimal recSilverNum;

    @ApiModelProperty("变后-钱包金币余额")
    private BigDecimal recgoldNum;

    @ApiModelProperty("变动前-打码量")
    private BigDecimal preCgdml;

    @ApiModelProperty("变动后-打码量")
    private BigDecimal afterCgdml;

    @ApiModelProperty("操作说明")
    private String opnote;

    @ApiModelProperty("创建时间")
    private Date createTime;








}
