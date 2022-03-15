package com.onelive.common.model.req.mem;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class MemUserGroupManageReq {

    @ApiModelProperty("注册开始时间")
    private Date startTime;

    @ApiModelProperty("注册结束时间")
    private Date endTime;

    @ApiModelProperty("国家编码")
    private Long currencyId;

    @ApiModelProperty("当前层级ID")
    private Long userGroupId;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    private String userNickName;

    @ApiModelProperty("用户状态-是否冻结 0否1是")
    private Boolean isFrozen;

    @ApiModelProperty("用户等级")
    private Integer userLevel;

    @ApiModelProperty("参数条件：0-全部（12345）、1-入款次数、2-入款总额、3-最大存款金额、4-出款次数、5-出款总额")
    private Integer paraWhere;

    @ApiModelProperty("参数条件最小值")
    private BigDecimal minNum;

    @ApiModelProperty("参数条件最大值")
    private BigDecimal maxNum;

    @ApiModelProperty("条数")
    private Integer pageSize;

    @ApiModelProperty("页数")
    private Integer pageNum;

}
