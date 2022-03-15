package com.onelive.common.model.vo.live;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播间详情展示类
 */
@Data
@ApiModel(value = "直播间详情展示类")
public class LiveRoomDetailVo {

	@ApiModelProperty("直播间ID")
	private Integer studioId;

	@ApiModelProperty("房间号码")
	private String studioNum;

	@ApiModelProperty("主播id")
	private Long anchorId;

	@ApiModelProperty("主播会员ID")
	private Long userId;

	@ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id）")
	private Integer productId;

	@ApiModelProperty("试看时长:超过时间后要付费")
	private Integer trySeeTime;

	@ApiModelProperty("弹幕价格")
	private BigDecimal barragePrice;

	@ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
	private Integer studioStatus;

	@ApiModelProperty("直播拉流地址")
	private String studioLivePath = "";

	@ApiModelProperty("直播间主播简单信息")
	private LiveAnchorVO LiveAnchorVO;

	@ApiModelProperty("在线人数")
	private Integer onlineNum = 0;

	@ApiModelProperty("火力值,计算方式：主播在直播间收到的总金币数 * 100")
	private Integer firepower;

	@ApiModelProperty("守护人数")
	private Integer guardNum = 0;

	@ApiModelProperty("主播开播时候，是否选择了游戏,false否true是")
	private Boolean isSelectGame = false;

	@ApiModelProperty(value = "彩票/游戏id")
	private Long gameId;

	@ApiModelProperty(value = "彩票/游戏图案地址")
	private String gameIcon;

	@ApiModelProperty(value = "彩票/游戏名称")
	private String gameName;

	@ApiModelProperty(value = "彩票/游戏开奖封盘时间（秒）")
	private Integer endTime;

	@ApiModelProperty(value = "直播间在线人数列表，默认值显示前50条")
	private List<LiveUserDetailVO> onlineList;

	@ApiModelProperty("直播间公共配置")
	private LiveRoomConfig roomConfig;
	
	@ApiModelProperty("彩票id")
    private Integer lotteryId;

}