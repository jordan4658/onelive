package com.onelive.manage.service.platform.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.platform.LiveFloatDto;
import com.onelive.common.model.dto.platform.LiveFloatLangDto;
import com.onelive.common.mybatis.entity.LiveFloat;
import com.onelive.common.mybatis.entity.LiveFloatLang;
import com.onelive.common.mybatis.mapper.master.platform.LiveFloatMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveFloatLangMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveFloatMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.platform.LiveFloatService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-20
 */
@Service
public class LiveFloatServiceImpl extends ServiceImpl<LiveFloatMapper, LiveFloat> implements LiveFloatService {

	@Resource
	private SlaveLiveFloatMapper slaveLiveFloatMapper;

	@Resource
	private SlaveLiveFloatLangMapper slaveLiveFloatLangMapper;

	@Override
	public List<LiveFloatDto> getList(LiveFloatDto liveFloatDto) {
		List<LiveFloat> selectList = slaveLiveFloatMapper.selectList(new QueryWrapper<LiveFloat>());
		List<LiveFloatDto> result = BeanCopyUtil.copyCollection(selectList, LiveFloatDto.class);
		for (LiveFloatDto liveFloat : result) {
			QueryWrapper<LiveFloatLang> queryWrapper = new QueryWrapper<LiveFloatLang>();
			queryWrapper.lambda().eq(LiveFloatLang :: getFloatId, liveFloat.getId());
			liveFloat.setLiveFloatLangList(BeanCopyUtil
					.copyCollection(slaveLiveFloatLangMapper.selectList(queryWrapper), LiveFloatLangDto.class));

		}

		return result;
	}

}
