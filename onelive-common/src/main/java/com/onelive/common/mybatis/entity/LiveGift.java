package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 礼物信息表
 * </p>
 *
 * @author ${author}
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveGift implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
      @TableId(value = "gift_id", type = IdType.AUTO)
    private Integer giftId;

    /**
     * 适用国家id,多个逗号分隔,空即:所有
     */
    private String useCountry;

    /**
     * 礼物平台币价格
     */
    private BigDecimal price;

    /**
     * 礼物字体颜色
     */
    private String fontColor;

    /**
     * 礼物动态图片地址
     */
    private String dynamicImage;

    /**
     * 礼物图片地址
     */
    private String imageUrl;

	/**
	 * 是否用户端展示 
	 */
	private Boolean status;
	
	/**
	 * 是否弹幕显示, 默认是
	 */
	private Boolean isDoubleHit;
	
    /**
     * 静态/动态 默认静态
     */
    private Boolean isDynamic;

    /**
     * 礼物图片展示方式 1.svga动画 2.lottie动画 3.骨骼动画
     */
    private Integer dynamicShowType;

    /**
     * 是否分成给主播,默认是
     */
    private Boolean isDivideAnchor;

    /**
     * 1：世界礼物，2：大型礼物，3：中型礼物，4：普通礼物，5：特权礼物 6：按时收费商品 7:按场收费商品 8:弹幕
     */
    private Integer giftType;

    /**
     * 是否弹幕显示, 默认是
     */
    private Boolean isBarrage;

    /**
     * 礼物停留时间,秒
     */
    private Integer stayTime;

    /**
     * 是否关联玩具,默认否
     */
    private Boolean isRelateToy;

    /**
     * 震动频率:远程控制主播震动棒,1~20个幅度
     */
    private Integer frequencyVibration;

    /**
     * 玩具的持续时间，时间为0.1~360秒
     */
    private Integer vibrationTime;

    /**
     * 礼物排序号
     */
    private Integer sortNum;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createdBy;


}
