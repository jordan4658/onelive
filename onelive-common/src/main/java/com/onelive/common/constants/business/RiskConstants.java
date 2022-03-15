package com.onelive.common.constants.business;

/**
 * @Classname MemConstant
 * @Des 会员相关常量
 */
public class RiskConstants {


    /** 审核状态--未审核 **/
    public static final int NOT_AUDIT = 0;
    /** 审核状态--锁定 **/
    public static final int LOCK = 1;
    /** 审核状态--通过 **/
    public static final int APPROVAL = 2;
    /** 审核状态--拒绝 **/
    public static final int REJECT = 3;


    /** 层级--默认层级 **/
    public static final int DEFAULT_LAYER = 0;
    /** 层级--土豪 **/
    public static final int TUHAO = 1;


    /** 提现状态--处理中 **/
    public static final int IN_PROGRESS = 1;
    /** 提现状态--成功 **/
    public static final int SUCCESS = 2;
    /** 提现状态--失败 **/
    public static final int FAIL = 3;
    /** 提现状态--取消 **/
    public static final int CANCEL = 4;

}
