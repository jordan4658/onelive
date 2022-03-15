package com.onelive.common.mybatis.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播日志表 实体类
 * 
 */
@Data
public class LiveStudioLog {
	/**
	 * 直播记录ID
	 */
	@TableId(value = "log_id", type = IdType.AUTO)
	private Integer logId;

	/**
	 * 主播ID
	 */
	private Long userId;
	
	
	@ApiModelProperty("商户编码")
	private String merchantCode;

	/**
	 * 直播间Num
	 */
	private String studioNum;
	/**
	 * 直播状态 0：关闭，1：直播中
	 */
	private Integer studioStatus;
	/**
	 * 本场直播有效观众数量
	 */
	private Integer effectiveAudience;

	/**
	 * 本场直播收到礼物打赏金币
	 */
	private BigDecimal moneyNumber;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 	本次直播时间，单位秒
	 */
	private Long liveTime;

	/**
	 * 本场直播录制视频播放url
	 */
	private String recordingVideoUrl;

	/**
	 * 	对应lottery_country的id
	 */
	private Long gameId;
	
	/**
	 * 	主播设备
	 */
	private String device;
	
	/**
	 * 	备注：下播原因 0：断线下播 1:主动下播 2 ：后台下播
	 */
	private Integer endReason;
	
	
	/**
	 * 	商品id（收费类型，以及收费金额，对应live_gift表的id）
	 */
	private Integer productId;
	
	
	/**
	 * 	直播收益订单号
	 */
	private String earningLogNo;
	
	/**
	 * 资金流水表订单号
	 */
	private String goldChangeNo;


}