package com.onelive.common.model.dto.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 首页直播相关数据DTO
 */
@Data
public class LiveStudioListForIndexDTO {
    @ApiModelProperty("直播间ID")
    private Integer studioId;

    @ApiModelProperty("房间号")
    private String studioNum;

    @ApiModelProperty("当前所在城市名")
    private String cityName;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("主播表id")
    private Integer anchorId;

    @ApiModelProperty("是否固定位置 1:是")
    private Boolean isFixed;

    @ApiModelProperty("推荐排序字段默认 0:一般排序 -1:推荐优先 2:置底")
    private Integer isFirst;

    @ApiModelProperty("游戏名")
    private String gameName;

    @ApiModelProperty("游戏id")
    private Long gameId;

    @ApiModelProperty("试看时长:超过时间后要付费")
    private Integer trySeeTime;

    @ApiModelProperty("在线登录观看人数")
    private Integer viewsNumber;

    @ApiModelProperty("画质")
    private String sharpness;

    @ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
    private Integer studioStatus;

    @ApiModelProperty("国家sys_country.country_code")
    private String countryCode;

    @ApiModelProperty("国家sys_country.country_name")
    private String countryName;

    @ApiModelProperty("默认0:绿播 1:黄播")
    private Integer colour;

    @ApiModelProperty("首页栏目ID—关联— live_column.columnCode")
    private String columnCode;

    @ApiModelProperty("直播间封面图（大图）")
    private String studioBackground;

    @ApiModelProperty("直播间封面图（小图,暂无用，如有小图设置此值）")
    private String studioThumbImage;

    @ApiModelProperty("直播间标题")
    private String studioTitle;

    @ApiModelProperty("直播间火力值")
    private Integer studioHeat;

    @ApiModelProperty("收费类型 6：按时收费 7:按场收费")
    private Integer chargeType;

    @ApiModelProperty("收费金额")
    private BigDecimal price;

    @ApiModelProperty("是否开通收费直播")
    private Boolean isCharge;

    @ApiModelProperty("是否光年推荐")
    private Boolean isLightYearRecommend;

    @ApiModelProperty("礼物金额")
    private Integer giftAmount;

    @ApiModelProperty("开播时间")
    private Date startTime;

    @ApiModelProperty("排序值")
    private Integer sortNum;

}
