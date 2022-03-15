package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayThreeProviderBackVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/17 16:34
 */
@Data
@ApiModel
public class PayThreeProviderBackVO {


    @ApiModelProperty("支付商id")
    private Long providerId;

    @ApiModelProperty("支付商名稱")
    private String providerName;

    @ApiModelProperty("支付类型：1-线上，2-线下（只取银行相关的数据）")
    private Integer providerType;

    @ApiModelProperty("平台设置的-商戶code")
    private String providerCode;

    @ApiModelProperty("第三方回调地址url")
    private String backUrl;

    @ApiModelProperty("回调ip白名单多个使用逗号分隔开")
    private String allowIps;

    @ApiModelProperty("三方支付订单查询url")
    private String getOrderUrl;

    @ApiModelProperty("第三方 支付下单url")
    private String addOrderUrl;

    @ApiModelProperty("第三方支付商戶ID  多个以英文逗号分隔")
    private String agentNo;

    @ApiModelProperty("第三方支付商应用ID")
    private String providerAppId;

    @ApiModelProperty("商戶密钥")
    private String secretCode;

    @ApiModelProperty("商戶公钥")
    private String pubSecret;

    @ApiModelProperty("商戶私钥")
    private String priSecret;

    @ApiModelProperty("银行名称标识符 如：ICBC")
    private String bankName;

    @ApiModelProperty("银行名称 如：招商银行")
    private String bankNameValue;

    @ApiModelProperty("银行卡账号")
    private String bankAccountNo;

    @ApiModelProperty("银行开户名称")
    private String bankAccountName;

    @ApiModelProperty("银行开户行地址")
    private String bankAddress;

    @ApiModelProperty("啟用狀態: 0啟用 9停用")
    private Integer status;

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
