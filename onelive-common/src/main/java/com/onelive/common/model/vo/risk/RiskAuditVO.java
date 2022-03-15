package com.onelive.common.model.vo.risk;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RiskAuditVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/6/4 16:50
 */
@Data
@ApiModel
public class RiskAuditVO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("分公司")
    private String branch;

    @ApiModelProperty("层级: 0-默认层级、1-土豪")
    private String layer;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("直接上级")
    private String directSuperior;

    @ApiModelProperty("会员id")
    private BigDecimal uid;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("所属总代")
    private String subAgent;

    @ApiModelProperty("出款备注")
    private String outRemark;

    @ApiModelProperty("取款次数")
    private Integer times;

    @ApiModelProperty("提出额度")
    private BigDecimal proposedQuota;

    @ApiModelProperty("手续费")
    private BigDecimal handleFee;

    @ApiModelProperty("优惠金额")
    private BigDecimal discAmount;

    @ApiModelProperty("行政费用")
    private BigDecimal adminFee;

    @ApiModelProperty("出款金额")
    private BigDecimal outAmount;

    @ApiModelProperty("usdt")
    private BigDecimal usdt;

    @ApiModelProperty("账户余额")
    private BigDecimal balance;

    @ApiModelProperty("户主")
    private String bankAccountName;

    @ApiModelProperty("银行")
    private String bankName;

    @ApiModelProperty("是否参加活动: 0-否、1-是")
    private String isAct;

    @ApiModelProperty("是否优惠扣除: 0-否、1-是")
    private String isDisc;

    @ApiModelProperty("审核状态: 0-未审核、1-锁定、2-通过、3-拒绝")
    private String status;

    @ApiModelProperty("投注ip")
    private String betIp;

    @ApiModelProperty("风控人")
    private String operator;

    @ApiModelProperty("提款发起时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

    @ApiModelProperty("风控确认时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    @ApiModelProperty("备注")
    private String remark;

}
