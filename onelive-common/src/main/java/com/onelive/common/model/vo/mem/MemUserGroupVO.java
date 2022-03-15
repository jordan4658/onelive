package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class MemUserGroupVO {

    @ApiModelProperty("用户层级主建ID")
    private Long userGroupId;

    @ApiModelProperty("国家编码")
    private String currencyCode;

    @ApiModelProperty("用户层级名称")
    private String groupName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否邀请返点（默认0）：0-否、1-是")
    private Boolean isInvitationRebates;

    @ApiModelProperty("出款次数（默认0）")
    private Long withdrawalsTimes;

    @ApiModelProperty("入款次数（默认99999999）")
    private Long depositTimes;

    @ApiModelProperty("生效开始时间")
    private Date startTime;

    @ApiModelProperty("生效结束时间")
    private Date endTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("最后更新人")
    private String updateUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后更新时间")
    private Date updateTime;

    @ApiModelProperty("用户层级配置信息")
    private List<MemGroupCurrencyCfgVO> groupCurrencyCfg;

    @ApiModelProperty("层级-用户数量")
    private Long userCount;
}
