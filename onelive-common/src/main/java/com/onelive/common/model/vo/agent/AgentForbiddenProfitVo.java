package com.onelive.common.model.vo.agent;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 禁止代理返点 查询结果实体类
 */
@Data
@ApiModel
public class AgentForbiddenProfitVo {

    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 用户账号
     */
    @ApiModelProperty("代理账号")
    private String userAccount;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registTime;

    /**
     * 禁止时间
     */
    @ApiModelProperty("禁止时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
