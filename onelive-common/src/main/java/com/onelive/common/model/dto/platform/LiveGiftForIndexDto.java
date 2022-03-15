package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "礼物用户端传输类")
public class LiveGiftForIndexDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("礼物id")
	private Integer giftId;
	
	@ApiModelProperty("语言标识")
	private String lang;

	@ApiModelProperty("礼物名字")
	private String giftName;

	@ApiModelProperty("礼物平台币价格")
	private BigDecimal price;

	@ApiModelProperty("礼物图片地址")
	private String imageUrl;

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
	 * 		适用国家id,多个逗号分隔,空即:所有
	 */
	private String useCountry;

	@ApiModelProperty("该礼物是否大图 ,默认否")
	private Boolean isGiftBig;

	@ApiModelProperty("是否分成给主播,默认是")
	private Boolean isDivideAnchor;

	@ApiModelProperty(" 商品类型：1：世界礼物，2：大型礼物，3：中型礼物，4：普通礼物，5：特权礼物 6：按时收费商品 7:按场收费商品")
	private Integer giftType;

	@ApiModelProperty("是否连击,默认是")
	private Boolean isDoubleHit;
	
	@ApiModelProperty("是否弹幕显示, 默认是")
	private Boolean isBarrage;

	@ApiModelProperty("震动频率:远程控制主播震动棒,1~20个幅度")
	private Integer frequencyVibration;

	@ApiModelProperty("玩具的持续时间，时间为0.1~360秒")
	private Integer vibrationTime;

	@ApiModelProperty("是否关联玩具,默认否")
	private Boolean isRelateToy;

	@ApiModelProperty("礼物连击ID（用于判断是否连击）")
	private String  giftComboId;

	@ApiModelProperty("礼物连击次数")
	private Integer giftComboNumb;

	@ApiModelProperty("共连击礼物数量")
	private Integer giftNumber;

}
