package com.onelive.common.model.vo.risk;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
public class RiskAuditExportVO {


    @ExcelProperty("分公司")
    @ColumnWidth(16)
    @ApiModelProperty("分公司")
    private String branch;

    @ExcelProperty("层级")
    @ColumnWidth(8)
    @ApiModelProperty("层级: 0-默认层级、1-土豪")
    private String layer;

    @ExcelProperty("订单编号")
    @ColumnWidth(40)
    @ApiModelProperty("订单编号")
    private String orderNo;

    @ExcelProperty("直接上级")
    @ColumnWidth(16)
    @ApiModelProperty("直接上级")
    private String directSuperior;

    @ExcelProperty("会员账号")
    @ColumnWidth(16)
    @ApiModelProperty("会员账号")
    private String account;

    @ExcelProperty("所属总代")
    @ColumnWidth(16)
    @ApiModelProperty("所属总代")
    private String subAgent;

    @ExcelProperty("出款备注")
    @ColumnWidth(8)
    @ApiModelProperty("出款备注")
    private String outRemark;

    @ExcelProperty("提出额度")
    @ColumnWidth(12)
    @ApiModelProperty("提出额度")
    private BigDecimal proposedQuota;

    @ExcelProperty("手续费")
    @ColumnWidth(12)
    @ApiModelProperty("手续费")
    private BigDecimal handleFee;

    @ExcelProperty("优惠金额")
    @ColumnWidth(12)
    @ApiModelProperty("优惠金额")
    private BigDecimal discAmount;

    @ExcelProperty("行政费用")
    @ColumnWidth(12)
    @ApiModelProperty("行政费用")
    private BigDecimal adminFee;

    @ExcelProperty("出款金额")
    @ColumnWidth(12)
    @ApiModelProperty("出款金额")
    private BigDecimal outAmount;

    @ExcelProperty("usdt")
    @ColumnWidth(12)
    @ApiModelProperty("usdt")
    private BigDecimal usdt;

    @ExcelProperty("账户余额")
    @ColumnWidth(12)
    @ApiModelProperty("账户余额")
    private BigDecimal balance;

    @ExcelProperty("户主")
    @ColumnWidth(8)
    @ApiModelProperty("户主")
    private String bankAccountName;

    @ExcelProperty("银行")
    @ColumnWidth(12)
    @ApiModelProperty("银行")
    private String bankName;

    @ExcelProperty("参加活动")
    @ColumnWidth(4)
    @ApiModelProperty("是否参加活动: 0-否、1-是")
    private String isAct;

    @ExcelProperty("优惠扣除")
    @ColumnWidth(4)
    @ApiModelProperty("是否优惠扣除: 0-否、1-是")
    private String isDisc;

    @ExcelProperty("审核")
    @ColumnWidth(8)
    @ApiModelProperty("审核状态: 0-未审核、1-锁定、2-通过、3-拒绝")
    private String status;

    @ExcelProperty("投注ip")
    @ColumnWidth(16)
    @ApiModelProperty("投注ip")
    private String betIp;

    @ExcelProperty("风控人")
    @ColumnWidth(16)
    @ApiModelProperty("风控人")
    private String operator;

    @ExcelProperty("提款发起时间")
    @ColumnWidth(20)
    @ApiModelProperty("提款发起时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

    @ExcelProperty("风控确认时间")
    @ColumnWidth(20)
    @ApiModelProperty("风控确认时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    @ExcelProperty("备注")
    @ColumnWidth(8)
    @ApiModelProperty("备注")
    private String remark;

    @ExcelProperty({"小计", "提出额度"})
    @ColumnWidth(12)
    @ApiModelProperty("提出额度")
    private BigDecimal sumPageProposedQuota;

    @ExcelProperty({"小计", "出款金额"})
    @ColumnWidth(12)
    @ApiModelProperty("出款金额")
    private BigDecimal sumPageOutAmount;

    @ExcelProperty({"小计", "优惠金额"})
    @ColumnWidth(12)
    @ApiModelProperty("优惠金额")
    private BigDecimal sumPageDiscAmount;

    @ExcelProperty({"总计", "提出额度"})
    @ColumnWidth(12)
    @ApiModelProperty("提出额度")
    private BigDecimal sumProposedQuota;

    @ExcelProperty({"总计", "出款金额"})
    @ColumnWidth(12)
    @ApiModelProperty("出款金额")
    private BigDecimal sumOutAmount;

    @ExcelProperty({"总计", "优惠金额"})
    @ColumnWidth(12)
    @ApiModelProperty("优惠金额")
    private BigDecimal sumDiscAmount;

    @ExcelProperty({"总计", "笔数"})
    @ColumnWidth(8)
    @ApiModelProperty("笔数")
    private BigDecimal totalCount;

}
