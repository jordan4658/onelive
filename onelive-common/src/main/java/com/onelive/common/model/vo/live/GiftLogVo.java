package com.onelive.common.model.vo.live;

import com.onelive.common.mybatis.entity.LiveGiftLog;

import lombok.Data;

/**
 * 礼物打赏记录表  实体类
 */
@Data
public class GiftLogVo extends LiveGiftLog {

	private static final long serialVersionUID = 1096984584236504206L;
	/**
	 * 礼物静态图片地址
	 */
	private String giftImage;
	/**
	 * 礼物接收人名称
	 */
	private String hostName;
	
	/**
	 * 商品类型：1：礼物商品，2：弹幕商品，3：打赏商品，4：其他商品（暂时），5一键打赏商品
	 */
	private Integer goodsType;
	
	/**
	 * 赠送时间
	 */
	private String givingDate;

}
