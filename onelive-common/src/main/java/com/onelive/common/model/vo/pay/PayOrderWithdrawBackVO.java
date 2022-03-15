package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayOrderWithdrawBackVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/21 15:14
 */
@Data
@ApiModel
public class PayOrderWithdrawBackVO {

    @ApiModelProperty("提现id")
    private Long withdrawId;

    @ApiModelProperty("银行卡id")
    private Long bankAccid;

    @ApiModelProperty("银行卡号")
    private Long bankAccountNo;

    @ApiModelProperty("提现订单号")
    private String withdrawNo;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("会员昵称")
    private String nickname;

    @ApiModelProperty("提现时间")
    private Date withdrawDate;

    @ApiModelProperty("提现金额")
    private BigDecimal withdrawAmt;

    @ApiModelProperty("提现状态:1-处理中  2-成功  3-失败 4-取消")
    private Integer withdrawStatus;

    @ApiModelProperty("操作说明例如：取消原因")
    private String operationExplain;

    @ApiModelProperty("打款金额")
    private BigDecimal payAmt;

    @ApiModelProperty("打款人")
    private String payMemName;

    @ApiModelProperty("打款时间")
    private Date payDate;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后修改人")
    private String updateUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
