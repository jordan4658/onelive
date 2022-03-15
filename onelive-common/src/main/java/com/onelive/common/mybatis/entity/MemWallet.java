package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemWallet implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户钱包id
     */
      @TableId(value = "wallet_id", type = IdType.AUTO)
    private Long walletId;

    /**
     * 钱包类型：1-平台钱包、2-OBG真人游戏 3.OBG体育游戏 4.OBG棋牌游戏 5.OBG捕鱼游戏 6.OBG老虎机游戏
     */
    private Integer walletType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 会员账号
     */
    private String account;

    /**
     * 钱包名称
     */
    private String walletName;

    /**
     * 金币（钱包余额，可以提现）
     */
    private BigDecimal amount;

    /**
     * 银豆（礼物使用，不可提现）
     */
    private BigDecimal silverBean;

    /**
     * 剩余打码量，打码量为0才能进行提现
     */
    private BigDecimal accountDml;

    /**
     * 累计打码量
     */
    private BigDecimal sumAccountDml;

    /**
     * 累计提现金额
     */
    private BigDecimal sumWithdrawAmount;

    /**
     * 总充值金额
     */
    private BigDecimal sumRechargeAmount;

    /**
     * 是否允许提现：0-否，1-是
     */
    private Boolean isWithdrawal;

    /**
     * 是否删除：0-否，1-是
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 最大充值金额
     */
    private BigDecimal payMax;

    /**
     * 首次充值金额
     */
    private BigDecimal payFirst;

    /**
     * 充值总次数
     */
    private Integer payNum;

    /**
     * 最大提现金额
     */
    private BigDecimal withdrawalMax;

    /**
     * 首次提现金额
     */
    private BigDecimal withdrawalFirst;

    /**
     * 提现总次数
     */
    private Integer withdrawalNum;


}
