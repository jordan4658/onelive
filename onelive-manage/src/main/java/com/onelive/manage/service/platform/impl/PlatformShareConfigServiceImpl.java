package com.onelive.manage.service.platform.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.platform.PlatformShareConfigDto;
import com.onelive.common.model.dto.platform.PlatformShareConfigLangDto;
import com.onelive.common.mybatis.entity.PlatformShareConfig;
import com.onelive.common.mybatis.entity.PlatformShareConfigLang;
import com.onelive.common.mybatis.mapper.master.platform.PlatformShareConfigMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlavePlatformShareConfigLangMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlavePlatformShareConfigMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.platform.PlatformShareConfigService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-23
 */
@Service
public class PlatformShareConfigServiceImpl extends ServiceImpl<PlatformShareConfigMapper, PlatformShareConfig>
		implements PlatformShareConfigService {

	@Resource
	private SlavePlatformShareConfigMapper slavePlatformShareConfigMapper;

	@Resource
	private SlavePlatformShareConfigLangMapper slavePlatformShareConfigLangMapper;

	@Override
	public List<PlatformShareConfigDto> getList() {
		List<PlatformShareConfig> list = slavePlatformShareConfigMapper
				.selectList(new QueryWrapper<PlatformShareConfig>());
		List<PlatformShareConfigDto> copyCollection = BeanCopyUtil.copyCollection(list, PlatformShareConfigDto.class);
		for (PlatformShareConfigDto platformShareConfig : copyCollection) {
			QueryWrapper<PlatformShareConfigLang> queryWrapper = new QueryWrapper<PlatformShareConfigLang>();
			queryWrapper.lambda().eq(PlatformShareConfigLang :: getShareId, platformShareConfig.getId());
			List<PlatformShareConfigLang> selectList = slavePlatformShareConfigLangMapper.selectList(queryWrapper);

			List<PlatformShareConfigLangDto> liveStudioListVoList = BeanCopyUtil.copyCollection(selectList,
					PlatformShareConfigLangDto.class);
			platformShareConfig.setPlatformShareConfigLangList(liveStudioListVoList);
			
		}
		return copyCollection;
	}

}
