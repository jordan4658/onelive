package com.onelive.common.model.vo.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 消息内容
 */
@Data
@ApiModel
public class MessageVo {
    @ApiModelProperty("消息ID")
    private Long id;

    @ApiModelProperty("消息类型 1.系统消息 2.邮件")
    private Integer msgType;

    @ApiModelProperty("接收类型 1按用户层级 2按用户等级 3按指定地区 4按指定账号")
    private Integer receiveType;
    /**
     * 接收者(多个用逗号分隔)
     */
    @ApiModelProperty("接收者(多个用逗号分隔)")
    private String receiver;
    /**
     * 图片地址
     */
    @ApiModelProperty("图片")
    private String img;
    /**
     * 跳转链接
     */
    @ApiModelProperty("跳转链接")
    private String skipUrl;
    /**
     * 活动开始时间
     */
    @ApiModelProperty("活动开始时间")
    private Date startDate;
    /**
     * 活动结束时间
     */
    @ApiModelProperty("活动结束时间")
    private Date endDate;

    @ApiModelProperty("多语言消息列表")
    private List<MessageItemVo> langList;

}
