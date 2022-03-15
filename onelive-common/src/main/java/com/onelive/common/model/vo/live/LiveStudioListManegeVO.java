package com.onelive.common.model.vo.live;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播列表后台管理返回类
 */
@Data
@ApiModel
public class LiveStudioListManegeVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("直播间ID")
	private Integer studioId;
	
	@ApiModelProperty("直播单次记录id")
	private Integer logId;
	
	@ApiModelProperty("房间号")
	private String studioNum;
	
	@ApiModelProperty("商户编码")
	private String merchantCode;
	
	@ApiModelProperty("上播地址")
	private String studioLivePushFlow;
	
    @ApiModelProperty("主播设备")
    private String device;
	
	@ApiModelProperty("国家sys_country.country_name")
	private String countryName;
	
	@ApiModelProperty("是否固定位置 1:是")
	private Boolean isFixed;
	
	@ApiModelProperty("是否推广 0:否 1:是")
	private Boolean isPromotion;
	
	@ApiModelProperty("更新时间")
	private Date updateTime;
	
//	@ApiModelProperty("是否置底 默认否")
//	private Boolean isBottom;

	@ApiModelProperty("游戏id")
	private Integer gameId;
	
	@ApiModelProperty("游戏名字")
	private String gameName;
	
	@ApiModelProperty("排序值,越小越前")
	private Integer sortNum;
	
	@ApiModelProperty("主播表id")
	private Integer anchorId;
	
	@ApiModelProperty("主播昵称")
	private String nickName;
	
	@ApiModelProperty("所属家族")
	private String familyName;
	
	@ApiModelProperty("直播游戏")
	private String liveGame;
	
	@ApiModelProperty("在线人数")
	private Integer onlineMem;
	
	/**
	 *	观看人数
	 */
	@ApiModelProperty("观看人数")
	private Integer watchMem;
	
	@ApiModelProperty("收到礼物金额")
	private BigDecimal giftMoney;
	
	@ApiModelProperty("投注金额")
	private BigDecimal betMoney;
	
	@ApiModelProperty("投注次数")
	private BigDecimal betCount;
	
	@ApiModelProperty("投注人数")
	private BigDecimal betUserCount;

	@ApiModelProperty("送礼人数")
	private Integer givingCount;
	
	@ApiModelProperty("用户ID")
	private Long userId;
	
	@ApiModelProperty("主播账号")
	private String userAccount;
	
	@ApiModelProperty("收费类型:null:免费   6:按时收费 7:按场收费 ")
	private Integer chargeType;
	
	@ApiModelProperty("试看时长:超过时间后要付费")
	private Integer trySeeTime;
	
	@ApiModelProperty("在线登录观看人数")
	private Integer viewsNumber;
	
	@ApiModelProperty("画质")
	private String sharpness;

	/**
	 * 直播间状态 0：未开播，1：开播，2：网络状态不好
	 */
	@ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
	private Integer studioStatus;

	@ApiModelProperty("国家sys_country.countryCode")
	private String countryCode;
	
	/**
	 * 默认0:绿播 1:黄播
	 */
	@ApiModelProperty("默认0:绿播 1:黄播")
	private Integer colour;

	@ApiModelProperty("首页栏目ID—关联— live_column.columnCode")
	private String columnCode;

	/**
	 * 直播间类型
	 */
	@ApiModelProperty("直播间类型")
	private String studioTypeId;


	@ApiModelProperty("推荐排序字段默认 0:一般排序 -1:推荐优先 2:置底")
	private Integer isFirst;

	/**
	 * 直播间标题
	 */
	@ApiModelProperty("直播间标题")
	private String studioTitle;
	/**
	 * 直播间火力值
	 */
	@ApiModelProperty("直播间火力值")
	private String studioHeat;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;
}