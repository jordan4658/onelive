package com.onelive.common.model.vo.operate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class MessageListVo {
    @ApiModelProperty("消息ID")
    private Long id;

    @ApiModelProperty("中文标题")
    private String title;

    @ApiModelProperty("消息类型 1平台公告 2活动")
    private Integer msgType;

    @ApiModelProperty("接收类型 1按用户层级 2按用户等级 3按指定地区 4按指定账号")
    private Integer receiveType;

    /**
     * 更新时间
     */
    @ApiModelProperty("操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty("操作人")
    private String updateUser;

}
