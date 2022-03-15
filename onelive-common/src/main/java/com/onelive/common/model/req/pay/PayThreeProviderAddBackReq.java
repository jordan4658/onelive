package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayThreeProviderAddBackReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/17 17:13
 */
@Data
@ApiModel
public class PayThreeProviderAddBackReq {


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

    @ApiModelProperty("商戶密钥")
    private String secretCode;

    @ApiModelProperty("商戶公钥")
    private String pubSecret;

    @ApiModelProperty("商戶私钥")
    private String priSecret;

    @ApiModelProperty("银行名称标识符 如：ICBC")
    private String bankName;

    @ApiModelProperty("银行卡账号")
    private String bankAccountNo;

    @ApiModelProperty("银行开户名称")
    private String bankAccountName;

    @ApiModelProperty("银行开户行地址")
    private String bankAddress;

    @ApiModelProperty("状态：1-启用，2-禁用")
    private Integer status;

}
