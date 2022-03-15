package com.onelive.common.model.vo.agent;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 邀请码查询结果实体类
 */
@Data
@ApiModel
public class AgentInviteCodeVo {

    private Long id;
    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userAccount;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String inviteCode;

    /**
     * 代理层级, 没有上线的是一级代理
     */
    @ApiModelProperty("代理层级")
    private Integer agentLevel;


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

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
