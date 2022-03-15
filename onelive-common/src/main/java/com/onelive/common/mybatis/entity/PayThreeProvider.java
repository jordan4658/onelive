package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 支付商設置
 * </p>
 *
 * @author ${author}
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayThreeProvider implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付商id
     */
      @TableId(value = "provider_id", type = IdType.AUTO)
    private Long providerId;

    /**
     * 支付商名称
     */
    private String providerName;

    /**
     * 支付类型：1-线上，2-线下（只取银行相关的数据）
     */
    private Integer providerType;

    /**
     * 平台设置的-商戶code
     */
    private String providerCode;

    /**
     * 第三方支付回调接口地址
     */
    private String backUrl;

    /**
     * 回调白名单，多个使用逗号分隔开
     */
    private String allowIps;

    /**
     * 三方支付订单查询url
     */
    private String getOrderUrl;

    /**
     * 第三方支付下单请求url
     */
    private String addOrderUrl;

    /**
     * 第三方支付的商戶ID  多个以英文逗号分隔
     */
    private String agentNo;

    /**
     * 第三方支付商应用ID
     */
    private String providerAppId;

    /**
     * 商戶密钥
     */
    private String secretCode;

    /**
     * 商戶公钥
     */
    private String pubSecret;

    /**
     * 商戶私钥
     */
    private String priSecret;

    /**
     * 银行名称标识符 如ICBC
     */
    private String bankName;

    /**
     * 银行卡账号
     */
    private String bankAccountNo;

    /**
     * 银行开户名称
     */
    private String bankAccountName;

    /**
     * 银行开户行地址
     */
    private String bankAddress;

    /**
     * 状态：1-启用，2-禁用
     */
    private Integer status;

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


}
