package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class PayGoldChangeDetailVO {

    @ApiModelProperty("交易记录ID")
    private Long goldChangId;

    @ApiModelProperty("订单状态  1-处理中  2-成功 3-失败 4-取消  5-申请中 6-超时 7-撤单")
    private Integer orderStatus;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("交易时间")
    private Date payDate;

    @ApiModelProperty("交易金额")
    private BigDecimal price;

    @ApiModelProperty("金额变动类型：1=银豆、2=金币、3-其他")
    private Integer goldType;

    @ApiModelProperty("交易类型")
    private String transactionType;

    @ApiModelProperty("项目名称（多语言）")
    private String projectName;


}
