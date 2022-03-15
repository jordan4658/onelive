package com.onelive.common.model.vo.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 家族表VO
 * </p>
 *
 */
@Data
@ApiModel
public class MemFamilyVO{

    @ApiModelProperty("家族主键")
    private Long id;

    @ApiModelProperty("家族名")
    private String familyName;

    @ApiModelProperty("家族账号")
    private String userAccount;

    @ApiModelProperty("登录密码")
    private String password;

     @ApiModelProperty("账号状态")
    private Boolean isFrozen;

     @ApiModelProperty("礼物抽成比例")
    private BigDecimal giftRatio;

     @ApiModelProperty("备注")
    private String familyRemark;

     @ApiModelProperty("所属地区")
    private Long countryName;

    @ApiModelProperty("创建时间")
    private String createTime;
}
