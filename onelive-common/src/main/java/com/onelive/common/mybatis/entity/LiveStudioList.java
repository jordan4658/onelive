package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 直播间表 实体类
 */
@Data
@ApiModel
public class LiveStudioList {
	
	/**
	 * 直播间ID
	 */
	@ApiModelProperty("直播间ID")
	@TableId(value = "studio_id", type = IdType.AUTO)
	private Integer studioId;

	@ApiModelProperty("房间号")
	private String studioNum;

	@ApiModelProperty("游戏id,对应lottery_country的lottery_id")
	private Long gameId;
	
	@ApiModelProperty("当前所在城市名")
	private String cityName;
	
	@ApiModelProperty("是否固定位置 0:否 1:是")
	private Boolean isFixed;
	
	@ApiModelProperty("推荐排序字段默认 0:一般排序 -1:推荐优先 2:置底")
	private Integer isFirst;
	
	@ApiModelProperty("是否推广 0:否 1:是")
	private Boolean isPromotion;
	
	@ApiModelProperty("排序值,越小越前")
	private Integer sortNum;
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty("用户ID")
	private Long userId;
	
	@ApiModelProperty("国家sys_country.countryCode")
	private String countryCode;
	
	/**
	 * 开播时候，主播所在的地区（目前精确到国家）
	 */
	@ApiModelProperty("开播时候，主播所在的地区（目前精确到国家）")
	private String studioOpenArea;

	/**
	 * 直播间状态 0：未开播，1：开播，2：网络状态不好
	 */
	@ApiModelProperty("直播间状态 0：未开播，1：开播，2：网络状态不好")
	private Integer studioStatus;
	
	@ApiModelProperty("商品id（收费类型，以及收费金额，对应live_gift表的id）")
	private Integer productId;
	
	@ApiModelProperty("试看时长:超过时间后要付费")
	private Integer trySeeTime;
	
	@ApiModelProperty("商户编码")
	private String merchantCode;

	@ApiModelProperty("画质")
	private String sharpness;

	@ApiModelProperty("默认0:绿播 1:黄播")
	private Integer colour;

	/**
	 * 直播音视频拉流地址
	 */
	@ApiModelProperty("直播音视频拉流地址")
	private String studioLivePath;

	/**
	 * 直播音频拉流地址
	 */
	@ApiModelProperty("直播音频拉流地址")
	private String studioLiveVoicePath;

	/**
	 * 直播推流地址
	 */
	@ApiModelProperty("直播推流地址")
	private String studioLivePushFlow;

	/**
	 * 首页分类栏目ID—关联— live_column.columnCode
	 */
	@ApiModelProperty("首页栏目ID—关联— live_column.columnCode")
	private String columnCode;
	
	/**
	 * 直播间标题
	 */
	@ApiModelProperty("直播间标题")
	private String studioTitle;

	/**
	 * 更新人
	 */
	@ApiModelProperty("更新人")
	private String updatedBy;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	private String createdBy;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;
	
	/**
	 * 开播时间
	 */
	@ApiModelProperty("开播时间")
	private Date startTime;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 直播间封面图(小图)
	 */
	@ApiModelProperty("直播间封面图(小图)")
	@TableField("studio_thumb_image")
	private String studioThumbImage;
	
	/**
	 * 直播间封面图
	 */
	@ApiModelProperty("直播间封面图（大图）")
	private String studioBackground;
	
}