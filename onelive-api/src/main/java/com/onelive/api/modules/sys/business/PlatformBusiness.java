package com.onelive.api.modules.sys.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.onelive.api.service.game.GameCategoryService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.model.dto.platformConfig.PlatformConfigDto;
import com.onelive.common.model.dto.platformConfig.PlatformConfigForIndexDto;
import com.onelive.common.model.dto.platformConfig.PlatformConfigLangDto;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.StringUtils;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PlatformBusiness {

	@Resource
	private SysParameterService sysParameterService;

	@Resource
	private GameCategoryService gameCategoryService;

	/**
	 * 	平台配置查询
	 * 
	 * @return
	 */
	public PlatformConfigForIndexDto getConfig() {
		PlatformConfigForIndexDto platformConfigForIndexDto = new PlatformConfigForIndexDto();

		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.PLATFORM_CONFIG);
		String paramValue = byCode.getParamValue();
		if (StringUtils.isNotBlank(paramValue)) {
			PlatformConfigDto platformConfigDto = JSON.parseObject(paramValue, PlatformConfigDto.class);
			platformConfigForIndexDto.setName(platformConfigDto.getName());
			platformConfigForIndexDto.setIsMaintained(platformConfigDto.getIsMaintained());
			platformConfigForIndexDto.setIsOpenRegister(platformConfigDto.getIsOpenRegister());
			platformConfigForIndexDto.setIsIndex(platformConfigDto.getIsIndex());
			List<PlatformConfigLangDto> contextLang = platformConfigDto.getContextLang();
			// 当前语言的
			if (CollectionUtil.isNotEmpty(contextLang)) {
				PlatformConfigLangDto platformConfigLangDto = contextLang.get(0);
				platformConfigForIndexDto.setLang(platformConfigLangDto.getLang());
				platformConfigForIndexDto.setContext(platformConfigLangDto.getContext());
			}
		}

		return platformConfigForIndexDto;
	}

}
