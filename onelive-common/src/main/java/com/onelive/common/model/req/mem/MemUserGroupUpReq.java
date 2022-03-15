package com.onelive.common.model.req.mem;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemUserGroupUpReq {

    @ApiModelProperty("用户层级主建ID")
    private Long userGroupId;

    @ApiModelProperty("国家编码")
    private String currencyCode;

    @ApiModelProperty("用户层级名称")
    private String groupName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否邀请返点：0-否、1-是")
    private Boolean isInvitationRebates;

    @ApiModelProperty("出款次数")
    private Long withdrawalsTimes;

    @ApiModelProperty("入款次数")
    private Long depositTimes;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("生效结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("用户层级配置信息")
    private List<MemGroupCurrencyCfgUpReq> groupCurrencyCfgUpReq;


}
