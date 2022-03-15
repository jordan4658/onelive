package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayHandleFinanceAmtBackReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/19 14:35
 */
@Data
@ApiModel
public class PayHandleFinanceAmtBackReq {

    @ApiModelProperty(value = "[必填]会员账号",required = true)
    private String account;

    @ApiModelProperty(value = "[必填]处理类型：10-减款、11-加款、12-减码、13-加码",required = true)
    private Integer handleType;

    @ApiModelProperty(value = "[必填]处理金额",required = true)
    private BigDecimal handleNum;

    @ApiModelProperty(value = "金额类型：1-银豆、2-金币")
    private Integer handleNumType;

    @ApiModelProperty(value = "[必填]是否进行打码：0-否、1-是",required = true)
    private Boolean isDm;

    @ApiModelProperty(value = "[必填]操作说明",required = true)
    private String handleExplain;



}
