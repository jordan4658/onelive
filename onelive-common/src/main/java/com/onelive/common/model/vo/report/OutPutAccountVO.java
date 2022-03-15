package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: OutPutAccountVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/5/4 15:22
 */
@Data
@ApiModel
public class OutPutAccountVO {

    @ApiModelProperty("线下入款金额")
    private BigDecimal offlineAmt;

    @ApiModelProperty("线上入款金额")
    private BigDecimal onlineAmt;

    @ApiModelProperty("活动金额")
    private BigDecimal activityAmt;

    @ApiModelProperty("返水金额")
    private BigDecimal backwaterAmt;

    @ApiModelProperty("手动加款金额")
    private BigDecimal artificialAddAmt;

    @ApiModelProperty("手动减款金额")
    private BigDecimal artificialSubAmt;

    @ApiModelProperty("提现金额")
    private BigDecimal withdrawAmt;




}
