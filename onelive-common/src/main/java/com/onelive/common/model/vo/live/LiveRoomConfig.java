package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播间详情展示类
 */
@Data
@ApiModel(value = "直播间公共配置")
public class LiveRoomConfig {

    //	增加 直播间公共配置：发言限制等级配置（2级），发送弹幕等级限制（10级），直播间关注主播弹窗配置（进入直播未关注主播 2分钟后弹出关注主播，停留5秒）
    @ApiModelProperty("发言限制等级")
    private Integer speakLevel;

    @ApiModelProperty("发言限制等级")
    private Integer barrageLevel;

    @ApiModelProperty("关注主播后多久弹出关注消息,单位秒")
    private Integer focusPopuTime;

    @ApiModelProperty("关注主播后弹出消息的停留时间,单位秒")
    private Integer focusStayTime;

    @ApiModelProperty("是否可进入 1可以进入，2：被踢 3：收费超时观看")
    private Integer enterType;

    @ApiModelProperty("剩余观看时长-(默认-1L)单位：秒")
    private Long remainingTime = -1L;

    @ApiModelProperty("退出直播间时是否显示关注弹窗")
    private Boolean isQuitRoomShowFocus;

    @ApiModelProperty("是否开启弹幕")
    private Boolean isOpenBarrage;

}