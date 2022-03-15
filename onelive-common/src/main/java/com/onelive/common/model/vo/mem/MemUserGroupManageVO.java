package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class MemUserGroupManageVO {


    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    private String userNickName;

    @ApiModelProperty("国家编码")
    private String currencyId;

    @ApiModelProperty("用户等级")
    private Integer userLevel;

    @ApiModelProperty("入款次数")
    private Long depositTimes;

    @ApiModelProperty("入款总额")
    private BigDecimal totalDeposit;

    @ApiModelProperty("最大存款金额")
    private BigDecimal maxDeposit;

    @ApiModelProperty("出款次数")
    private Long withdrawalsTimes;

    @ApiModelProperty("出款总额")
    private BigDecimal totalDispensing;

    @ApiModelProperty("用户状态-是否冻结 0否1是")
    private Boolean isFrozen;

    @ApiModelProperty("用户加入时间")
    private Date registerTime;

}
