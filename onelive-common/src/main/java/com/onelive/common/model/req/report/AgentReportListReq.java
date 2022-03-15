package com.onelive.common.model.req.report;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询代理报表请求参数
 */

@Data
@ApiModel
public class AgentReportListReq extends PageReq {

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

}
