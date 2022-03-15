package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName DepositOnlineVO
 * @Desc 入款支付方式报表VO类
 * @Date 2021/4/23 9:49
 */
@Data
@ApiModel
public class DepositOnlineVO {
    
    @ApiModelProperty("支付设定名称")
    private String payTypeName;
    @ApiModelProperty("支付商名称")
    private String providerName;
    @ApiModelProperty("支付方式")
    private String payWayName;
    @ApiModelProperty("人数")
    private String peopleNum;
    @ApiModelProperty("成功次数")
    private String successNum;
    @ApiModelProperty("失败次数")
    private String failNum;
    @ApiModelProperty("成功率")
    private String successRate;
    @ApiModelProperty("入款总额")
    private String depositAmount;
}    
    