package com.onelive.common.model.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 活动列表查询实体类
 */
@Data
@ApiModel
public class SysAdvActivityListVO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("适用地区")
    private String countryNameList;

    /**
     * 活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗
     */
    @ApiModelProperty("活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗")
    private Integer activityType;

    /**
     * 跳转模块 0无 1链接 2活动 3游戏
     */
    @ApiModelProperty("跳转模块 0无 1链接 2活动 3游戏")
    private String skipModel;

    /**
     * 状态 0启用1禁用
     */
    @ApiModelProperty("状态 false启用 true禁用")
    private Boolean isFrozen;

    /**
     * 活动开始时间
     */
    @ApiModelProperty("活动开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 活动结束时间
     */
    @ApiModelProperty("活动结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
