package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: MemGoldchangeVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/15 17:55
 */
@Data
@ApiModel
public class MemGoldchangeVO {

    @ApiModelProperty("交易金额单位:元")
    private BigDecimal price;

    @ApiModelProperty("交易类型名称")
    private String transactionTypeName;

    @ApiModelProperty("交易类型图标")
    private String transactionTypeIconUrl;

    @ApiModelProperty("支付类型名称")
    private String payWayName;

    @ApiModelProperty("银行卡号")
    private String bankNo;

    @ApiModelProperty("交易时间 date")
    private Date payDate;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单状态 1-处理中  2-成功  3-失败 4-取消")
    private Integer orderStatus;

}
