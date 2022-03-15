package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayTypeBackVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/8 11:50
 */
@Data
@ApiModel
public class PayTypeBackVO {

    @ApiModelProperty("支付类型id")
    private Long payTypeId;

    @ApiModelProperty("支付类型code：1-支付宝、2-微信、3-银联")
    private Integer payTypeCode;

    @ApiModelProperty("支付类型名称")
    private String payTypeName;

    @ApiModelProperty("支付类型图标url")
    private String iconUrl;

    @ApiModelProperty("是否启用：0-否，1-是")
    private Integer isEnable;

    @ApiModelProperty("是否热门：0-否，1-是")
    private Integer isHot;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("最后更新人")
    private String updateUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后更新时间")
    private Date updateTime;



}
