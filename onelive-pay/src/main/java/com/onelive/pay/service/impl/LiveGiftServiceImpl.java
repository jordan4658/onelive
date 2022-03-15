package com.onelive.pay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.mybatis.entity.LiveGift;
import com.onelive.common.mybatis.mapper.master.platform.LiveGiftMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveGiftMapper;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.pay.common.utils.ApiBusinessRedisUtils;
import com.onelive.pay.service.LiveGiftService;

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
		List<LiveGiftForIndexDto> cacheData = ApiBusinessRedisUtils.get(RedisCacheableKeys.GIFT_LIST + "countryCode_" + countryCode + "_lang_" + lang);
		if(!CollectionUtils.isEmpty(cacheData)){
			return cacheData;
		}
		List<LiveGiftForIndexDto> listForIndex = slaveLiveGiftMapper.getListForIndex(countryCode, lang);
		for (LiveGiftForIndexDto target : listForIndex) {
			target.setImageUrl(AWSS3Util.getAbsoluteUrl(target.getImageUrl()));
			target.setDynamicImage(AWSS3Util.getAbsoluteUrl(target.getDynamicImage()));
		}
		ApiBusinessRedisUtils.set(RedisCacheableKeys.GIFT_LIST + "countryCode_" + countryCode + "_lang_" + lang, listForIndex);
		return listForIndex;
	}

}
