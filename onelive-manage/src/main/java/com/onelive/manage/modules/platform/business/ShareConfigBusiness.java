package com.onelive.manage.modules.platform.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.PlatformShareConfigDto;
import com.onelive.common.mybatis.entity.PlatformShareConfig;
import com.onelive.common.mybatis.entity.PlatformShareConfigLang;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.platform.PlatformShareConfigLangService;
import com.onelive.manage.service.platform.PlatformShareConfigService;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShareConfigBusiness {

	@Resource
	private PlatformShareConfigService platformShareConfigService;

	@Resource
	private PlatformShareConfigLangService platformShareConfigLangService;

	public List<PlatformShareConfigDto> getList() {
		List<PlatformShareConfigDto> list = platformShareConfigService.getList();
		return list;
	}

	public void update(PlatformShareConfigDto platformShareConfigDto, LoginUser loginUser) {
		if (platformShareConfigDto.getId() == null) {
			throw new BusinessException("分享类型id不能为空");
		}

		PlatformShareConfig already = platformShareConfigService.getById(platformShareConfigDto.getId());
		if (already == null) {
			throw new BusinessException("找不到对应的分享类型信息");
		}
		PlatformShareConfig platformShareConfig = new PlatformShareConfig();
		BeanUtils.copyProperties(platformShareConfigDto, platformShareConfig);
		platformShareConfig.setUpdateUser(loginUser.getAccLogin());
		platformShareConfigService.saveOrUpdate(platformShareConfig);

		// 更新分享类型多语言信息
		List<PlatformShareConfigLang> platformShareConfigLangList = BeanCopyUtil
				.copyCollection(platformShareConfigDto.getPlatformShareConfigLangList(), PlatformShareConfigLang.class);
		platformShareConfigLangList.forEach(t -> {
			t.setShareId(platformShareConfigDto.getId());
			platformShareConfigLangService.saveOrUpdate(t);
		});
	}

}
