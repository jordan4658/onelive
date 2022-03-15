package com.onelive.common.constants.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 支付相关常量类
 * @date 2021/3/26
 */
public class PayConstants {

    /**
     * 平台回调网关域名 业务参数代码
     */
    public static final String PAY_CALLBACK_HTTP = "PAY_CALLBACK_HTTP";
    /**
     * 交易记录的类型 业务参数代码
     */
    public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    /**
     * 查询全部交易记录 业务参数代码
     */
    public static final String ALL_TRANSACTION_TYPE = "ALL_TRANSACTION_TYPE";
    /**
     * 预计提现到账间隔时间 单位：分钟 业务参数代码
     */
    public static final String TX_ACCOUNTING_TIME = "TX_ACCOUNTING_TIME";

    /**
     * 平台金币单位 业务参数代码
     */
    public static final String PLATFORM_GOLD_UNIT = "PLATFORM_GOLD_UNIT";
    /**
     * 平台银豆单位 业务参数代码
     */
    public static final String PLATFORM_SILVER_UNIT = "PLATFORM_SILVER_UNIT";
    /**
     * 金币兑换银豆汇率配置 业务参数代码
     */
    public static final String GOLD_SILVER_EXCHANGE = "GOLD_SILVER_EXCHANGE";

    //系统操作员
    public static final String SYS = "sys";
    public static final Integer KEY_0 = 0;
    public static final Integer KEY_1 = 1;
    /**
     * 银行卡转换成隐式字符串 例如 ：6214830119460961 ->  **** **** **** 0961
     */
    public static final String CONVERT_STR = "**** **** **** ";


    //----------------交易记录类型-----------start------------------
    /**
     * 充值
     */
    public static final int RECHARGE_TYPE = 1;
    /**
     * 提现
     */
    public static final int WITHDRAW_TYPE = 2;
    /**
     * 返水
     */
    public static final int BACKWATER_TYPE = 3;
    //----------------交易记录类型-----------end------------------


    /**
     * 地雷支付成功状态码
     */
    public static final String DILEI_SUCCESS = "00";
    /**
     * 富盈支付成功状态码
     */
    public static final String FUYING_SUCCESS = "2";
    /**
     * VGPAY支付成功状态码
     */
    public static final int VGPAY_SUCCESS = 10;


    // ------------------支付渠道代码--------start-------------------
    /**
     * 银行卡转账
     */
    public static final String PAY_BANK_TRANSFER = "202101";
//    /** 地雷支付 */
//    public static final String PAY_DILEI = "202102";
    /**
     * KG支付
     */
    public static final String PAY_KG = "202102";
    // ------------------支付渠道代码--------end---------------------


    /**
     * 订单状态 1-处理中  2-成功  3-失败 4-取消
     */
    public enum PayOrderStatusEnum {

        /**
         * 1- 订单处理中 (处理中)
         */
        IN_HAND("PROCESSING", 1, "订单处理中"),

        /**
         * 2- 订单完成 (成功)
         */
        SUCCESS("COMPLETED", 2, "订单完成"),

        /**
         * 3- 订单错误 (失败)，请参考orderMessage。
         */
        UN_SUCCESS("ERROR", 3, "订单错误"),

        /**
         * 4-订单已取消 (取消)
         */
        CANCEL("CANCELED", 4, "订单已取消"),

        /**
         * 5-订单等待中 (申请中)
         */
        PENDING("PENDING", 5, "订单等待中"),

        /**
         * 6- 订单逾时。逾时的订单仍有可
         */
        TIMEOUT("TIMEOUT", 6, "订单超时"),

        /**
         * 7- 订单逾时。逾时的订单仍有可
         */
        CANCELLATIONS("cancellations", 7, "撤单");


        private Integer code; //平台状态
        private String value; //支付商订单状态
        private String mgs; //状态描述

        PayOrderStatusEnum(String value, Integer code, String mgs) {
            this.code = code;
            this.value = value;
            this.mgs = mgs;
        }

        public Integer getCode() {
            return this.code;
        }

        public String getValue() {
            return this.value;
        }

        public String getMgs() {
            return this.mgs;
        }

    }


    /**
     * 支付类型 code：1-MOMO、2-ZALO、3-银联
     */
    public enum PayTypeEnum {
        /**
         * MOMO
         */
        ALIPAY(1),
        /**
         * ZALO
         */
        WECHAT(2),
        /**
         * 银联
         */
        BANK(3);

        private Integer code;

        PayTypeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

    }

    /**
     * 赠送类型：0-不赠送，1-首充赠送，2-每次赠送
     */
    public enum givingTypeEnum {
        /**
         * 不赠送
         */
        NOT_GIVING(0),
        /**
         * 首充赠送
         */
        FIRST_GIVING(1),
        /**
         * 每次赠送
         */
        EVERY_TIME_GIVING(2);

        private Integer code;

        givingTypeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

    }

    /**
     * 支付商类型： 1-线上 2-线下
     */
    public enum PayProviderTypeEnum {
        /**
         * 线上
         */
        ONLINE(1),
        /**
         * 线下
         */
        OFFLINE(2);

        private Integer code;

        PayProviderTypeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

    }

    /**
     * 启用禁用 1-启用，2-禁用
     */
    public enum StatusEnum {
        /**
         * 启用
         */
        ENABLE(1),
        /**
         * 禁用
         */
        NOT_ENABLE(2);

        private Integer code;

        StatusEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

    }

    /**
     * 充值类型 1-微信、2-支付宝、3-银行卡转账  还有其他的充值类型进行继续添加
     * 充值订单号前缀：微信支付-czvx、支付宝-czap、银行卡转账-czbk  还有其他的充值类型进行继续添加
     */
    public enum RechargePrefixEnum {
        /**
         * 微信充值订单号前缀
         */
        wechat(1, "CZVX"),
        /**
         * 支付宝充值订单号前缀
         */
        alipay(2, "CZAP"),
        /**
         * 银行卡转账充值订单号前缀
         */
        bank(3, "CZBK");

        //充值类型
        private Integer payTypeCode;
        //充值类型订单前缀
        private String code;

        RechargePrefixEnum(Integer payTypeCode, String code) {
            this.code = code;
            this.payTypeCode = payTypeCode;
        }

        public String getCode() {
            return this.code;
        }

        public Integer getPayTypeCode() {
            return this.payTypeCode;
        }

    }
    /**
     * 变动类型 1-充值成功、2-签到奖励、3-提现申请、4-提现取消、5-提现失败、 6-提现处理中 、7-提现成功、8-充值赠送、
     * 9-活动奖励、10-手动提现、11-手动充值、12-手动打码、13-手动加码、15-充值失败、16-充值取消
     * 19-KY上分 、20-KY下分、21-彩票下注、22-彩票中奖、23-用户彩票撤单、24-管理员彩票撤单
     * 25-礼物打赏、26-弹幕、27-关注主播、28-金币兑换银豆、29-直播间门票购买、30-代理下级返点、31-彩票未中奖，32-主播关播统计收入、
     * 33-家族长提现名下主播余额，34-主播被家族长提现余额、35-充值处理中，36-充值等待中，37-充值订单超时,38-用户钱包划转，39-第三方游戏注单账变
     */
    @Getter
    @AllArgsConstructor
    public enum AccountChangTypeEnum {
        /**
         * 充值
         */
        CHANG_TYPE1(1, "充值成功"),
        /**
         * 签到奖励
         */
        CHANG_TYPE2(2, "签到奖励"),
        /**
         * 提现申请
         */
        CHANG_TYPE3(3, "提现申请"),
        /**
         * 提现取消
         */
        CHANG_TYPE4(4, "提现取消"),
        /**
         * 提现失败
         */
        CHANG_TYPE5(5, "提现失败"),
        /**
         * 提现处理中
         */
        CHANG_TYPE6(6, "提现处理中"),
        /**
         * 提现成功
         */
        CHANG_TYPE7(7, "提现成功"),
        /**
         * 充值赠送
         */
        CHANG_TYPE8(8, "充值赠送"),
        /**
         * 活动奖励
         */
        CHANG_TYPE9(9, "活动奖励"),
        /**
         * 手动提现
         */
        CHANG_TYPE10(10, "手动提现"),
        /**
         * 手动充值
         */
        CHANG_TYPE11(11, "手动充值"),
        /**
         * 手动减码
         */
        CHANG_TYPE12(12, "手动减码"),
        /**
         * 手动加码
         */
        CHANG_TYPE13(13, "手动加码"),
        /**
         * 充值失败
         */
        CHANG_TYPE15(15, "充值失败"),
        /**
         * 充值取消
         */
        CHANG_TYPE16(16, "充值取消"),
        /**
         * KY上分
         */
        CHANG_TYPE19(19, "KY上分"),
        /**
         * KY下分
         */
        CHANG_TYPE20(20, "KY下分"),
        /**
         * 彩票下注
         */
        CHANG_TYPE21(21, "彩票下注"),
        /**
         * 彩票开奖
         */
        CHANG_TYPE22(22, "彩票中奖"),
        /**
         * 用户彩票撤单
         */
        CHANG_TYPE23(23, "用户彩票撤单"),
        /**
         * 管理员彩票撤单
         */
        CHANG_TYPE24(24, "管理员彩票撤单"),
        /**
         * 礼物打赏
         */
        CHANG_TYPE25(25, "礼物打赏"),
        /**
         * 弹幕
         */
        CHANG_TYPE26(26, "弹幕"),
        /**
         * 关注主播
         */
        CHANG_TYPE27(27, "关注主播"),
        /**
         * 金币兑换银豆
         */
        CHANG_TYPE28(28, "金币兑换银豆"),
        /**
         * 直播间门票购买
         */
        CHANG_TYPE29(29, "直播间门票购买"),
        /**
         * 代理下级返点
         */
        CHANG_TYPE30(30, "代理下级返点"),
        /**
         * 彩票中奖
         */
        CHANG_TYPE31(31, "彩票未中奖"),
        /**
         * 主播关播统计收入
         */
        LIVE_EARNING32(32, "主播关播统计收入"),
        /**
         * 	家族长提现名下主播余额
         */
        FAMILY_WITHDRAW_ANCHOR33(33, "家族长提现名下主播余额"),
        /**
         * 	主播被家族长提现余额
         */
        FAMILY_WITHDRAW_ANCHOR34(34, "主播被家族长提现余额"),
        /**
         * 	充值处理中
         */
        CHANG_TYPE35(35, "充值处理中"),
        /**
         * 	充值等待中
         */
        CHANG_TYPE36(36, "充值等待中"),
        /**
         * 	订单超时
         */
        CHANG_TYPE37(37, "订单超时"),
        /**
         * 	钱包划转
         */
        CHANG_TYPE38(38, "用户钱包划转"),
        /**
         * 	第三方游戏注单账变
         */
        CHANG_TYPE39(39, "第三方游戏注单账变"),

        ;


        //账变类型
        private Integer payTypeCode;
        //账变描述
        private String msg;

//        AccountChangTypeEnum(Integer payTypeCode) {
//            this.payTypeCode = payTypeCode;
//        }
//        public Integer getPayTypeCode() {
//            return this.payTypeCode;
//        }

        public static String getMsgByCode(Integer code) {
            for (AccountChangTypeEnum type : values()) {
                if (type.getPayTypeCode().equals(code)) {
                    return type.getMsg();
                }
            }
            return "";
        }
    }



    /**
     * 收支类型: 0-收入,1-支出，2-其他
     */
    public enum FlowTypeEnum {
        /**
         * 收入
         */
        INCOME(0),
        /**
         * 支出
         */
        DISBURSE(1),
        /**
         * 其他
         */
        OTHER(2);

        private Integer code;

        FlowTypeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

    }

    /**
     * 处理类型：10-减款、11-加款、12-减码、13-加码、
     */
    public enum HandleTypeEnum {

        /**
         * 减款
         */
        SUBTRACT_AMT(10, "SDRM"),
        /**
         * 加款
         */
        ADD_AMT(11, "SDSM"),
        /**
         * 减码
         */
        SUBTRACT_DML(12, "SDRD"),
        /**
         * 加码
         */
        ADD_DML(13, "SDSD");

        private Integer code;

        private String value;

        HandleTypeEnum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public Integer getCode() {
            return this.code;
        }

        public String getValue() {
            return this.value;
        }

    }


    /**
     * 金额变动类型：1=银豆、2=金币、3=金币兑换银豆
     */
    public enum GoldTypeEnum {

        /**
         * 银豆
         */
        GOLD_TYPE_1(1),
        /**
         * 金币
         */
        GOLD_TYPE_2(2),
        /**
         * 金币兑换银豆
         */
        GOLD_TYPE_3(3);

        private Integer code;

        GoldTypeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }


    }


    public enum TRANSACTION_TYPE_ENUM {
        /**
         * 充值
         */
        TYPE_1(1),
        /**
         * 提现
         */
        TYPE_2(2),
        /**
         * 礼物
         */
        TYPE_3(3),
        /**
         * 注单
         */
        TYPE_4(4),
        /**
         * 中奖
         */
        TYPE_5(5),
        /**
         * 其他
         */
        TYPE_6(6),
        /**
         * 优惠
         */
        TYPE_7(7),
        /**
         * 活动
         */
        TYPE_8(8),
        /**
         * 兑换
         */
        TYPE_9(9),
        /**
         * 全部
         */
        TYPE_0(0),
        ;
        private Integer code;

        TRANSACTION_TYPE_ENUM(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

    }


    public enum TransactionStatusEnum {
        //成功
        STATUS_1(1),
        //失败
        STATUS_2(2),
        //取消
        STATUS_3(3),
        //处理中
        STATUS_4(4),
        //申请中
        STATUS_5(5);

        private Integer code;

        TransactionStatusEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }
    }


}
