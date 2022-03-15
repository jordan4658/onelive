package com.onelive.api.service.platform.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.platform.LiveFloatLangService;
import com.onelive.common.model.dto.platform.LiveFloatForIndexDto;
import com.onelive.common.mybatis.entity.LiveFloatLang;
import com.onelive.common.mybatis.mapper.master.platform.LiveFloatLangMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveFloatMapper;
import com.onelive.common.utils.upload.AWSS3Util;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-20
 */
@Service
public class LiveFloatLangServiceImpl extends ServiceImpl<LiveFloatLangMapper, LiveFloatLang>
		implements LiveFloatLangService {

	@Resource
	private SlaveLiveFloatMapper slaveLiveFloatMapper;

	@Override
	public List<LiveFloatForIndexDto> getFloatList(String countryCode, String lang) {
		List<LiveFloatForIndexDto> floatList = slaveLiveFloatMapper.getFloatList(countryCode, lang);
		for (LiveFloatForIndexDto liveFloatForIndexDto : floatList) {
			liveFloatForIndexDto.setImgUrl(AWSS3Util.getAbsoluteUrl(liveFloatForIndexDto.getImgUrl()));
		}
		return slaveLiveFloatMapper.getFloatList(countryCode, lang);
	}

}
