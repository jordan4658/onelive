package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWayVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/8 12:00
 */
@Data
@ApiModel
public class PayWayVO {

    @ApiModelProperty("支付方式主键id")
    private Long payWayId;

    @ApiModelProperty("充值汇率")
    private String czExChange;

    @ApiModelProperty("币种单位")
    private String currencyUnit;

    @ApiModelProperty("金币单位")
    private String shortcutOptionsUnit;

    @ApiModelProperty("支付商id")
    private Long providerId;

    @ApiModelProperty("支付类型code：1-MOMO、2-ZALO、3-银联")
    private Integer payTypeCode;

    @ApiModelProperty("支付方式的名称")
    private String payWayName;

    @ApiModelProperty("支付方式图标")
    private String payWayIcon;

    @ApiModelProperty("赠送类型： 0-不赠送，1-首充赠送，2-每次赠送")
    private Integer givingType;

    @ApiModelProperty("赠送比例 %")
    private BigDecimal payWayGivingRatio;

    @ApiModelProperty("单笔最低充值金额")
    private BigDecimal minAmt;

    @ApiModelProperty("单笔最高充值金额")
    private BigDecimal maxAmt;

    @ApiModelProperty("是否允许输入金额：0-否，1-是")
    private Boolean isInput;

    @ApiModelProperty("支付方式的渠道集合")
    private List<PayChannelVO> payChannelVOList;

}
