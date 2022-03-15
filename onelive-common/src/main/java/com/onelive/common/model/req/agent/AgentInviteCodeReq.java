package com.onelive.common.model.req.agent;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 邀请码查询参数
 */
@Data
@ApiModel
public class AgentInviteCodeReq extends PageReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "上级账号")
    private String inviteUserAccount;

    @ApiModelProperty(value = "商户编码")
    private String merchantCode;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
