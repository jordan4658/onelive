package com.onelive.api.service.platform.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.platform.LiveGiftService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.model.dto.platform.ProductChargeDto;
import com.onelive.common.model.dto.platform.ProductTypeDto;
import com.onelive.common.mybatis.entity.LiveGift;
import com.onelive.common.mybatis.mapper.master.platform.LiveGiftMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveGiftMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.upload.AWSS3Util;

/**
 * <p>
 * 礼物信息表 服务实现类
 * </p>
 *
 * @author maomao
 * @since 2021-11-03
 */
@Service
public class LiveGiftServiceImpl extends ServiceImpl<LiveGiftMapper, LiveGift> implements LiveGiftService {

	@Resource
	private SlaveLiveGiftMapper slaveLiveGiftMapper;

	@Override
	public List<LiveGiftForIndexDto> getList(String countryCode, String lang) {
		List<LiveGiftForIndexDto> cacheData = ApiBusinessRedisUtils
				.get(RedisCacheableKeys.GIFT_LIST + "countryCode_" + countryCode + "_lang_" + lang);
		if (!CollectionUtils.isEmpty(cacheData)) {
			return cacheData;
		}
		List<LiveGiftForIndexDto> listForIndex = slaveLiveGiftMapper.getListForIndex(countryCode, lang);
		for (LiveGiftForIndexDto target : listForIndex) {
			target.setImageUrl(AWSS3Util.getAbsoluteUrl(target.getImageUrl()));
			target.setDynamicImage(AWSS3Util.getAbsoluteUrl(target.getDynamicImage()));
		}
		ApiBusinessRedisUtils.set(RedisCacheableKeys.GIFT_LIST + "countryCode_" + countryCode + "_lang_" + lang,
				listForIndex);
		return listForIndex;
	}

	@Override
	public LiveGiftDto getByIdAndLang(Integer giftId, String lang) {
		List<LiveGiftForIndexDto> cacheData = ApiBusinessRedisUtils
				.get(RedisCacheableKeys.LIVE_GIFT_LIST + "countryCode_" + LoginInfoUtil.getCountryCode() + "_lang_" + lang);
		if (!CollectionUtils.isEmpty(cacheData)) {
			Optional<LiveGiftForIndexDto> findFirst = cacheData.stream().filter(t -> t.getGiftId().equals(giftId)).findFirst();
			if (findFirst.isPresent()) {
				LiveGiftDto result = new LiveGiftDto();
				BeanUtils.copyProperties(findFirst.get(), result);
				return result;
			}
		}
		return slaveLiveGiftMapper.getByIdAndLang(giftId, lang);
	}

	@Override
	public LiveGift selectById(Integer productId) {
		return slaveLiveGiftMapper.selectById(productId);
	}

	@Override
	public List<ProductChargeDto> roomProducts(ProductTypeDto productTypeDto) {
		QueryWrapper<LiveGift> queryWrapper = new QueryWrapper<LiveGift>();
		queryWrapper.lambda().eq(LiveGift::getGiftType, productTypeDto.getType());
		queryWrapper.lambda().eq(LiveGift::getStatus, 1).orderByAsc(LiveGift::getPrice);

		List<LiveGift> selectList = slaveLiveGiftMapper.selectList(queryWrapper);
		List<ProductChargeDto> result = new ArrayList<>(selectList.size());
		for (LiveGift liveGift : selectList) {
			ProductChargeDto productChargeDto = new ProductChargeDto();
			productChargeDto.setGiftId(liveGift.getGiftId());
			productChargeDto.setPrice(liveGift.getPrice());
			result.add(productChargeDto);
		}
		return result;
	}

	@Override
	public LiveGift getBarrage() {
		LiveGift cacheData = ApiBusinessRedisUtils.get(RedisCacheableKeys.GIFT_BARRAGE);
		if (cacheData != null) {
			return cacheData;
		}
		QueryWrapper<LiveGift> queryWrapper = new QueryWrapper<LiveGift>();
		queryWrapper.lambda().eq(LiveGift::getGiftType, 8).last("LIMIT 1");
		LiveGift selectOne = slaveLiveGiftMapper.selectOne(queryWrapper);
		ApiBusinessRedisUtils.set(RedisCacheableKeys.GIFT_BARRAGE, selectOne);
		return selectOne;
	}

	@Override
	public List<LiveGiftForIndexDto> getLiveRoomList(String countryCode, String lang) {
		List<LiveGiftForIndexDto> cacheData = ApiBusinessRedisUtils
				.get(RedisCacheableKeys.LIVE_GIFT_LIST + "countryCode_" + countryCode + "_lang_" + lang);
		if (!CollectionUtils.isEmpty(cacheData)) {
			return cacheData;
		}
		List<LiveGiftForIndexDto> listForIndex = slaveLiveGiftMapper.getLiveRoomList(countryCode, lang);
		for (LiveGiftForIndexDto target : listForIndex) {
			target.setImageUrl(AWSS3Util.getAbsoluteUrl(target.getImageUrl()));
			target.setDynamicImage(AWSS3Util.getAbsoluteUrl(target.getDynamicImage()));
		}
		ApiBusinessRedisUtils.set(RedisCacheableKeys.LIVE_GIFT_LIST + "countryCode_" + countryCode + "_lang_" + lang,
				listForIndex);
		return listForIndex;
	}

}
