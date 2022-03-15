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
 * 会员金额变动明细
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemGoldchange implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 账户变动明细id
     */
    @TableId(value = "gold_chang_id", type = IdType.AUTO)
    private Long goldChangId;

    /**
     * 账变订单号
     */
    private String goldChangeOrderNo;


    /**
     * 金额变动类型：1=银豆、2=金币,3-其他
     */
    private Integer goldType;

    /**
     * 账变钱包类型：1-本平台钱包、2-im体育钱包
     */
    private Integer changWalletType;



    /**
     * 相关号 如充值订单号、提现订单号、投注单号
     */
    private String  refNo;

    /**
     * 会员账号
     */
    private String account;

    /**
     * 变动类型 1-充值、2-签到奖励、3-提现申请、4-提现取消、5-投注、6-投注取消、7-其他钱包转入平台钱包、8-平台钱包转出到其他钱包、9-活动奖励、10-手动提现、11-手动充值、12-手动打码、13-手动加码
     */
    private Integer changeType;

    /**
     * 转入的钱包id，钱包与钱包之间转账使用-与转出的钱包id 相对应
     */
    private Long transferInWalletId;

    /**
     * 转出的钱包id ，钱包与钱包之间转账使用-与转入的钱包id 相对应
     */
    private Long transferOutWalletId;

    /**
     * 账变前-钱包金币余额
     */
    private BigDecimal goldNum;
    /**
     * 账变前-钱包银豆余额
     */
    private BigDecimal silverNum;

    /**
     * 账变金币金额
     */
    private BigDecimal amount;
    /**
     * 账变银豆金额
     */
    private BigDecimal silverAmount;

    /**
     * 账变后-钱包金币余额
     */
    private BigDecimal recgoldNum;
    /**
     * 账变后-钱包银豆余额
     */
    private BigDecimal recSilverNum;

    /**
     * 变动前-打码量
     */
    private BigDecimal preCgdml;

    /**
     * 变动后-打码量
     */
    private BigDecimal afterCgdml;

    /**
     * 操作说明
     */
    private String opnote;

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
     * 收支类型 0收入1支出
     */
    private Integer flowType;

    /**
     * 第三方对应的操作类型（例如IM体育：结算有很多种，对应相应的结算状态）
     */
    private Integer thirdType;


}
