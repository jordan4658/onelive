package com.onelive.common.model.vo.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邀请码查询结果实体类
 */
@Data
@ApiModel
public class AgentInviteCodeInfoVo {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userAccount;


    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String inviteCode;

    //TODO 还没有层级
    @ApiModelProperty("所属层级")
    private String group;

    /**
     * 邀请用户账号
     */
    @ApiModelProperty("上级账号")
    private String inviteUserAccount;

    /**
     * 状态:1正常,0封停
     */
    @ApiModelProperty("状态:1正常,0封停")
    private Boolean status;


}
