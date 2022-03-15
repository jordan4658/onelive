package com.onelive.common.model.req.mem;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class MemUserGroupAddReq {

    @ApiModelProperty("国家编码")
    private String currencyCode;

    @ApiModelProperty("用户层级名称")
    private String groupName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否邀请返点（默认0）：0-否、1-是")
    private Boolean isInvitationRebates;

    @ApiModelProperty("出款次数（默认0）")
    private Long withdrawalsTimes=0L;

    @ApiModelProperty("入款次数（默认99999999）")
    private Long depositTimes = 99999999L;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("生效结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("用户层级配置信息")
    private List<MemGroupCurrencyCfgAddReq> groupCurrencyCfgAddReq;

}
