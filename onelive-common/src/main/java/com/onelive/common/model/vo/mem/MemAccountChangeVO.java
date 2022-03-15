package com.onelive.common.model.vo.mem;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PublicAccountChangeVO
 * @Description: 公共账变接口
 * @date 创建时间：2021/4/13 11:03
 */
@Data
public class MemAccountChangeVO {

    /**
     * 账变用户账号 必填
     */
    private String account;

    /**
     * 是否银豆账变，1-银豆，2-金币、3-金币兑换银豆,4-用户钱包划转、5-第三方游戏注单
     */
    private Integer isSilverBean;

    /**
     * 必填
     * 变动类型 1-充值成功、2-签到奖励、3-提现申请、4-提现取消、5-提现失败、 6-提现处理中 、7-提现成功、8-充值赠送、
     * 9-活动奖励、10-手动提现、11-手动充值、12-手动打码、13-手动加码、15-充值失败、16-充值取消
     * 19-KY上分 、20-KY下分、21-彩票下注、22-彩票中奖、23-用户彩票撤单、24-管理员彩票撤单
     * 25-礼物打赏、26-弹幕、27-关注主播、28-金币兑换银豆、29-直播间门票购买、30-代理下级返点、31-彩票未中奖，32-主播关播统计收入、
     * 33-家族长提现名下主播余额，34-主播被家族长提现余额、35-充值处理中，36-充值等待中，37-充值订单超时,38-用户钱包划转，39-第三方游戏注单账变
     */
    private Integer changeType;

    /**
     * 第三方对应的操作类型（例如IM体育：结算有很多种，对应相应的结算状态）
     */
    private Integer thirdType;

    /**
     * 必填
     * 账变金币金额 单位：个
     */
    private BigDecimal price =new BigDecimal("0.0000");

    /**
     *
     * 账变银豆金额 单位：个
     */
    private BigDecimal silverBeanPrice=new BigDecimal("0.0000");

    /**
     * 必填
     * 打码量
     */
    private BigDecimal dml=new BigDecimal("0.0000");

    /**
     * 必填
     * 相关订单号（充值-充值订单号、提现-提现订单号）
     */
    private String orderNo;

    /**
     * 流水号（第三方充值使用）
     */
    private String flowNo;

    /**
     * 必填
     * 收支类型：0-收入,1-支出，2-其他
     */
    private Integer flowType;

    /**
     * 转入的钱包id（钱包与钱包转账需要）
     */
    private Long transferInWalletId;

    /**
     * 转出钱包id（钱包与钱包转账需要）
     */
    private Long transferOutWalletId;

    /**
     * 必填（
     * 账变说明
     */
    private String opNote;


}
