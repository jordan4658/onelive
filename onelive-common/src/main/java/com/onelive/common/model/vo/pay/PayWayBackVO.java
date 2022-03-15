package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWayBackVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/17 10:03
 */
@Data
@ApiModel
public class PayWayBackVO {

    @ApiModelProperty("支付方式主键id")
    private Long payWayId;

    @ApiModelProperty("支付商id")
    private Long providerId;

    @ApiModelProperty("支付商类型：1=线上、2=线下")
    private Integer providerType;

    @ApiModelProperty("国家编码（区分支付方式是那个国家的）")
    private String countryCode;

    @ApiModelProperty("支付商名称")
    private String providerName;

    @ApiModelProperty("开户行地址")
    private String bankAddress;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("开户名")
    private String bankAccountName;

    @ApiModelProperty("支付类型code：1-支付宝、2-微信、3-银联")
    private Integer payTypeCode;

    @ApiModelProperty("支付类型名称")
    private String payTypeName;

    @ApiModelProperty("支付方式的名称")
    private String payWayName;

    @ApiModelProperty("支付方式的标识区分是h5还是扫码")
    private String payWayTag;

    @ApiModelProperty("赠送类型： 0-不赠送，1-首充赠送，2-每次赠送")
    private Integer givingType;

    @ApiModelProperty("赠送比例 %")
    private BigDecimal payWayGivingRatio;

    @ApiModelProperty("单笔最低充值金额")
    private BigDecimal minAmt;

    @ApiModelProperty("单笔最高充值金额")
    private BigDecimal maxAmt;

    @ApiModelProperty("快捷充值选项，多个使用“,”分隔开")
    private String shortcut;

    @ApiModelProperty("是否允许输入金额：0-否，1-是")
    private Boolean isInput;

    @ApiModelProperty("状态：1-启用，2-禁用")
    private Integer status;

    @ApiModelProperty("排序字段")
    private Long sortBy;

    @ApiModelProperty("创建人账号")
    private String createUser;

    @ApiModelProperty("最后更新人账号")
    private String updateUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后更新时间")
    private Date updateTime;

}
