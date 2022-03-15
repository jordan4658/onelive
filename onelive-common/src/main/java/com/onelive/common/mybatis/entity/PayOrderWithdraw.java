package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 提现申请
 * </p>
 *
 * @author ${author}
 * @since 2021-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayOrderWithdraw implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提现id
     */
    @TableId(value = "withdraw_id", type = IdType.AUTO)
    private Long withdrawId;

    /**
     * 银行账户id
     */
    private Long bankAccid;

    /**
     * 提现订单号
     */
    private String withdrawNo;

    /**
     * 会员账号
     */
    private String account;

    /**
     * 提现时间
     */
    private Date withdrawDate;

    /**
     * 提现金币数量
     */
    private BigDecimal withdrawAmt;

    /**
     * 实际提现金额（对应国家币种）
     */
    private BigDecimal actualPayment;

    /**
     * 提现-平台币兑换汇率
     */
    private String txExchange;

    /**
     * 国家code（用于区分那个国家的币种汇率）
     */
    private String currencyCode;


    /**
     * 提现状态:1-处理中  2-成功  3-失败 4-取消
     */
    private Integer withdrawStatus;

    /**
     * 操作说明例如：取消原因
     */
    private String operationExplain;

    /**
     * 打款金额
     */
    private BigDecimal payAmt;

    /**
     * 打款人()
     */
    private String payMemName;

    /**
     * 打款时间
     */
    private Date payDate;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否首提 false否true是
     */
    private Boolean isFirst;


}
