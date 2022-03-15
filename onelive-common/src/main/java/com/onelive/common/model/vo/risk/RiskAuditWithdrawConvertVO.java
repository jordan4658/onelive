package com.onelive.common.model.vo.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RiskAuditVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/6/4 16:50
 */
@Data
@ApiModel
public class RiskAuditWithdrawConvertVO {

    @ApiModelProperty("提现id")
    private Long withdrawId;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("银行账户id")
    private Long bankAccid;

    @ApiModelProperty("会员昵称")
    private String nickname;

    @ApiModelProperty("提现订单号")
    private String withdrawNo;

    @ApiModelProperty("提现状态:1-处理中 2-成功 3-失败 4-取消")
    private String withdrawStatus;

    @ApiModelProperty("打款时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String payDate;

    @ApiModelProperty("提现金额")
    private BigDecimal withdrawAmt;

    @ApiModelProperty("提现时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String withdrawDate;

}
