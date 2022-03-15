package com.onelive.common.model.vo.live;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播历史后台管理返回类
 */
@Data
@ApiModel
public class LiveLogVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("房间号")
	private String studioNum;
	
	@ApiModelProperty("游戏id")
	private Integer gameId;
	
	@ApiModelProperty("游戏name")
	private String gameName;
	
	@ApiModelProperty("主播设备")
	private String device;
	
	@ApiModelProperty("备注：下播原因 0：断线下播 1:主动下播 2 ：后台下播")
	private Integer endReason;
	
	@ApiModelProperty("直播时长，单位秒")
	private Integer liveTime;
	
	@ApiModelProperty("主播昵称")
	private String nickName;
	
	@ApiModelProperty("所属家族")
	private String familyName;
	
	@ApiModelProperty("语言标志")
	private String lang;
	
	@ApiModelProperty("本场直播有效观众数量，人数")
	private Integer effectiveAudience;
	
	@ApiModelProperty("收到礼物金额")
	private BigDecimal giftMoney;
	
	@ApiModelProperty("投注金额")
	private BigDecimal betMoney;
	
	@ApiModelProperty("投注次数")
	private Integer betCount;
	
	@ApiModelProperty("家族id")
	private Long familyId;
	
	@ApiModelProperty("送礼人数")
	private Integer givingCount;
	
	@ApiModelProperty("用户ID")
	private Long userId;
	
	@ApiModelProperty("直播时间开始，单位秒")
	private Long liveTimeStart;
	
	@ApiModelProperty("直播时间结束，单位秒")
	private Long liveTimeEnd;
	
	@ApiModelProperty("主播账号")
	private String userAccount;
	
	@ApiModelProperty("商户号")
	private String merchantCode;
	
	@ApiModelProperty("查询用，收费类型:0:免费 1 :按时收费 2:按场收费 ")
	private Integer chargeType;

	@ApiModelProperty("国家sys_country.countryCode")
	private String countryCode;
	
	@ApiModelProperty("默认0:绿播 1:黄播")
	private Integer colour;

	@ApiModelProperty("直播间标题")
	private String studioTitle;

	@ApiModelProperty("开始时间")
	private String startTime;
	
	@ApiModelProperty("结束时间")
	private String endTime;
	
    @ApiModelProperty("是否开始直播间是 false : 查询直播间结束时间")
    private Boolean isStartTime;

    @ApiModelProperty("查询类型 1 投注金额 2 投注次数 3本场礼物 ")
	private Integer queryType;
	
	@ApiModelProperty("特殊查询条件开始")
	private Integer queryTypeParamStrat;
	
	@ApiModelProperty("特殊查询条件结束 ")
	private Integer queryTypeParamEnd;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;
}