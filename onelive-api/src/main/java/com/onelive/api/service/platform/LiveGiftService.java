package com.onelive.api.service.platform;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.model.dto.platform.ProductChargeDto;
import com.onelive.common.model.dto.platform.ProductTypeDto;
import com.onelive.common.mybatis.entity.LiveGift;

/**
 * <p>
 * 礼物信息表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-03
 */
public interface LiveGiftService extends IService<LiveGift> {

	/**
	 * 查询礼物列表
	 * @return
	 */
	List<LiveGiftForIndexDto> getList(String countryCode, String lang);

	/**
	 *   根据id和lang返回礼物信息
	 * 
	 * @param giftId
	 * @param lang
	 * @return
	 */
	LiveGiftDto getByIdAndLang(Integer giftId, String lang);

	/**
	 * 	根据礼物id查询
	 * 
	 * @param productId
	 * @return
	 */
	LiveGift selectById(Integer productId);

	/**
	 * 
	 * 	查询直播间收费商品
	 * @return
	 */
	List<ProductChargeDto> roomProducts(ProductTypeDto productTypeDto);

	/**
	 * 	获取弹幕类型的礼物
	 * @return
	 */
	LiveGift getBarrage();

	/**
	 * 查询直播间礼物列表
	 * @param countryCode
	 * @param lang
	 * @return
	 */
	List<LiveGiftForIndexDto> getLiveRoomList(String countryCode, String lang);
}
