package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 代理报表数据查询结果实体类
 */
@Data
public class AgentReportListVo {

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("注册人数(新)")
    private Integer newRegistCount;

    @ApiModelProperty("注册人数(总)")
    private Integer totalRegistCount;

    //    银行卡充值金额/笔数/人数（新）
    @ApiModelProperty("银行卡充值金额(新)")
    private BigDecimal newAmountRechargeByCard = new BigDecimal("0");
    @ApiModelProperty("银行卡充值笔数(新)")
    private Integer newCountRechargeByCard = 0;
    @ApiModelProperty("银行卡充值人数(新)")
    private Integer newUserCountRechargeByCard = 0;
    //    二维码充值金额/笔数/人数（新）
    @ApiModelProperty("二维码充值金额(新)")
    private BigDecimal newAmountRechargeByQrCode = new BigDecimal("0");
    @ApiModelProperty("二维码充值笔数(新)")
    private Integer newCountRechargeByQrCode = 0;
    @ApiModelProperty("二维码充值人数(新)")
    private Integer newUserCountRechargeByQrCode = 0;
    //    第二方充值金额/笔数/人数（新）
    @ApiModelProperty("第二方充值金额(新)")
    private BigDecimal newAmountRechargeBySecond = new BigDecimal("0");
    @ApiModelProperty("第二方充值笔数(新)")
    private Integer newCountRechargeBySecond = 0;
    @ApiModelProperty("第二方充值人数(新)")
    private Integer newUserCountRechargeBySecond = 0;
    //    代充充值金额/笔数/人数（新）
    @ApiModelProperty("代充充值金额(新)")
    private BigDecimal newAmountRechargeByOther = new BigDecimal("0");
    @ApiModelProperty("代充充值笔数(新)")
    private Integer newCountRechargeByOther = 0;
    @ApiModelProperty("代充充值人数(新)")
    private Integer newUserCountRechargeByOther = 0;
    //    出款金额/笔数/人数（新）
    @ApiModelProperty("出款金额(新)")
    private BigDecimal newAmountWithdrawal = new BigDecimal("0");
    @ApiModelProperty("出款笔数(新)")
    private Integer newCountWithdrawal = 0;
    @ApiModelProperty("出款人数(新)")
    private Integer newUserCountWithdrawal = 0;
    //    银行卡充值金额/笔数/人数（总）
//    二维码充值金额/笔数/人数（总）
//    第二方充值金额/笔数/人数（总）
//    代充充值金额/笔数/人数（总）
//    出款金额/笔数/人数（总）
//    银行卡充值金额/笔数/人数（新）
    @ApiModelProperty("银行卡充值金额(总)")
    private BigDecimal totalAmountRechargeByCard = new BigDecimal("0");
    @ApiModelProperty("银行卡充值笔数(总)")
    private Integer totalCountRechargeByCard = 0;
    @ApiModelProperty("银行卡充值人数(总)")
    private Integer totalUserCountRechargeByCard = 0;
    //    二维码充值金额/笔数/人数（新）
    @ApiModelProperty("二维码充值金额(总)")
    private BigDecimal totalAmountRechargeByQrCode = new BigDecimal("0");
    @ApiModelProperty("二维码充值笔数(总)")
    private Integer totalCountRechargeByQrCode = 0;
    @ApiModelProperty("二维码充值人数(总)")
    private Integer totalUserCountRechargeByQrCode = 0;
    //    第二方充值金额/笔数/人数（新）
    @ApiModelProperty("第二方充值金额(总)")
    private BigDecimal totalAmountRechargeBySecond = new BigDecimal("0");
    @ApiModelProperty("第二方充值笔数(总)")
    private Integer totalCountRechargeBySecond = 0;
    @ApiModelProperty("第二方充值人数(总)")
    private Integer totalUserCountRechargeBySecond = 0;
    //    代充充值金额/笔数/人数（新）
    @ApiModelProperty("代充充值金额(总)")
    private BigDecimal totalAmountRechargeByOther = new BigDecimal("0");
    @ApiModelProperty("代充充值笔数(总)")
    private Integer totalCountRechargeByOther = 0;
    @ApiModelProperty("代充充值人数(总)")
    private Integer totalUserCountRechargeByOther = 0;
    //    出款金额/笔数/人数（新）
    @ApiModelProperty("出款金额(总)")
    private BigDecimal totalAmountWithdrawal = new BigDecimal("0");
    @ApiModelProperty("出款笔数(总)")
    private Integer totalCountWithdrawal = 0;
    @ApiModelProperty("出款人数(总)")
    private Integer totalUserCountWithdrawal = 0;

}
