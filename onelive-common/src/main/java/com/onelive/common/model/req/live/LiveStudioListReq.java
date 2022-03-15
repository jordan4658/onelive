package com.onelive.common.model.req.live;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播列表
 */
@Data
@ApiModel
public class LiveStudioListReq implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 直播间ID
	 */
	@ApiModelProperty("直播间ID")
	private Integer studioId;
	
	@ApiModelProperty("房间号")
	private String studioNum;
	
	@ApiModelProperty("所属家族")
	private String familyName;
	
	@ApiModelProperty("主播账号")
	private String userAccount;
	
	@ApiModelProperty("商户编码")
	private String merchantCode;
	
	@ApiModelProperty("是否固定位置 1:是")
	private Boolean isFixed;
	
	@ApiModelProperty("是否推广 0:否 1:是")
	private Boolean isPromotion;

	@ApiModelProperty("游戏id")
	private Integer gameId;
	
	@ApiModelProperty("排序值,越小越前")
	private Integer sortNum;
	
	@ApiModelProperty("主播昵称")
	private String nickName;
	
	@ApiModelProperty("直播游戏")
	private String liveGame;
	
	@ApiModelProperty("在线人数")
	private Integer onlineMem;
	
	@ApiModelProperty("观看人数")
	private Integer watchMem;
	
	@ApiModelProperty("收到礼物金额")
	private BigDecimal giftMoney;
	
	@ApiModelProperty("投注金额")
	private BigDecimal betMoney;
	
	@ApiModelProperty("投注次数")
	private BigDecimal betCount;

	@ApiModelProperty("送礼人数")
	private Integer givingCount;
	
	@ApiModelProperty("用户ID")
	private Long userId;
	
	@ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id）")
	private Integer productId;
	
	@ApiModelProperty("试看时长:超过时间后要付费")
	private Integer trySeeTime;
	
	private Integer viewsNumber;
	
	@ApiModelProperty("画质")
	private String sharpness;

	@ApiModelProperty("直播间状态 0：未开播，1：开播")
	private Integer studioStatus;

	@ApiModelProperty("国家sys_country.countryCode")
	private String countryCode;
	
	@ApiModelProperty("默认0:绿播 1:黄播")
	private Integer colour;

	@ApiModelProperty("首页栏目ID—关联— live_column.columnCode")
	private String columnCode;
	
	@ApiModelProperty("语言标志")
	private String lang;

	/**
	 * 直播间类型—关联— live_studio_type.studio_type_id
	 */
	@ApiModelProperty("直播间类型—关联— live_studio_type.studio_type_id")
	private String studioTypeId;

	@ApiModelProperty("推荐排序字段默认 0:一般排序 -1:推荐优先 2:置底")
	private Integer isFirst;

	@ApiModelProperty("直播间标题")
	private String studioTitle;

	@ApiModelProperty("直播间火力值")
	private String studioHeat;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
    
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