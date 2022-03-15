package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物传输类")
public class LiveGiftDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("礼物id")
	private Integer giftId;

	@ApiModelProperty("礼物多语言数组")
	private List<LiveGiftLangDto> liveGiftLangList;

	/**
	 * 语言标识
	 */
	@ApiModelProperty("语言标识")
	private String lang;

	/**
	 * 礼物名字
	 */
	@ApiModelProperty("礼物名字")
	private String giftName;

	/**
	 * 礼物平台币价格
	 */
	@ApiModelProperty("礼物平台币价格")
	private BigDecimal price;

	/**
	 * 适用国家id,多个逗号分隔,空即:所有
	 */
	@ApiModelProperty("适用国家id,多个逗号分隔,空即:所有")
	private String useCountry;

	/**
	 * 礼物图片地址
	 */
	@ApiModelProperty("礼物图片地址")
	private String imageUrl;

	/**
	 * 礼物字体颜色
	 */
	@ApiModelProperty("礼物字体颜色")
	private String fontColor;

	/**
	 * 礼物动态图片地址
	 */
	@ApiModelProperty("礼物动态图片地址")
	private String dynamicImage;

	/**
	 * 礼物图片展示方式 1.svga动画 2.lottie动画 3.骨骼动画
	 */
	@ApiModelProperty("礼物图片展示方式 1.svga动画 2.lottie动画 3.骨骼动画")
	private Integer dynamicShowType;

	/**
	 * 礼物图片展示方式
	 */
	@ApiModelProperty("礼物图片展示方式")
	private String giftShow;

	/**
	 * 静态/动态 默认静态
	 */
	@ApiModelProperty("静态/动态 默认静态")
	private Boolean isDynamic;
	
	/**
	 * 是否用户端展示 
	 */
	@ApiModelProperty("是否用户端展示 默认是")
	private Boolean status;

	/**
	 * 礼物排序号
	 */
	@ApiModelProperty("礼物排序号")
	private Integer sortNum;

	/**
	 * 该礼物是否大图 ,默认否
	 */
	@ApiModelProperty("该礼物是否大图 ,默认否")
	private Boolean isGiftBig;

	/**
	 * 是否分成给主播,默认是
	 */
	@ApiModelProperty("是否分成给主播,默认是")
	private Boolean isDivideAnchor;

	@ApiModelProperty("商品类型：1：世界礼物，2：大型礼物，3：中型礼物，4：普通礼物，5：特权礼物 6：按时收费商品 7:按场收费商品 8:弹幕")
	private Integer giftType;

	/**
	 * 是否弹幕显示, 默认是
	 */
	@ApiModelProperty("是否连击,默认是")
	private Boolean isDoubleHit;
	
	/**
	 * 是否弹幕显示, 默认是
	 */
	@ApiModelProperty("是否弹幕显示, 默认是")
	private Boolean isBarrage;

	/**
	 * 震动频率:远程控制主播震动棒,1~20个幅度
	 */
	@ApiModelProperty("震动频率:远程控制主播震动棒,1~20个幅度")
	private Integer frequencyVibration;

	/**
	 * 玩具的持续时间，时间为0.1~360秒
	 */
	@ApiModelProperty("玩具的持续时间，时间为0.1~360秒")
	private Integer vibrationTime;

	/**
	 * 是否关联玩具,默认否
	 */
	@ApiModelProperty("是否关联玩具,默认否")
	private Boolean isRelateToy;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;
	
	@ApiModelProperty("礼物连击ID（用于判断是否连击）")
	private String  giftComboId;

	@ApiModelProperty("礼物连击次数")
	private Integer giftComboNumb;

	@ApiModelProperty("共连击礼物数量")
	private Integer giftNumber;

}
