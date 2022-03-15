package com.onelive.manage.modules.platform.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platformConfig.PlatformConfigAllDto;
import com.onelive.common.model.dto.platformConfig.RebatesConfigDto;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.game.GameCategoryService;
import com.onelive.manage.service.platform.LiveFloatLangService;
import com.onelive.manage.service.platform.LiveFloatService;
import com.onelive.manage.service.sys.SysParameterService;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PlatformBusiness {

	@Resource
	private LiveFloatService liveFloatService;

	@Resource
	private LiveFloatLangService liveFloatLangService;

	@Resource
	private SysParameterService sysParameterService;

	@Resource
	private GameCategoryService gameCategoryService;

	public List<PlatformConfigAllDto> getList() {
		List<SysParameterListVO> listByType = sysParameterService.getListByType(SysParameterConstants.PLATFORM);
		List<PlatformConfigAllDto> result = new ArrayList<PlatformConfigAllDto>(listByType.size());
		for (SysParameterListVO sysParameterListVO : listByType) {
			// 如果是返点，查询三方游戏平台
			if (SysParameterConstants.REBATES_RATIO.equals(sysParameterListVO.getParamCode())) {
				// 游戏平台，最新数据
				List<GameCategory> list = gameCategoryService.getList();
				List<RebatesConfigDto> resultRebates = new ArrayList<>(list.size());
				// 默认所有最新的游戏平台
				for (GameCategory gameCategory : list) {
					RebatesConfigDto rebatesConfigDto = new RebatesConfigDto();
					rebatesConfigDto.setGameCategoryId(gameCategory.getId());
					rebatesConfigDto.setRebates(new BigDecimal("0"));
					resultRebates.add(rebatesConfigDto);
				}

				// 已经配置的游戏平台
				String paramValue = sysParameterListVO.getParamValue();
				if (StringUtils.isNotBlank(paramValue)) {
					List<RebatesConfigDto> rebatesConfigDtoList = JSON.parseArray(paramValue, RebatesConfigDto.class);
					// 设置配置的游戏平台的返点比例
					for (RebatesConfigDto rebatesConfigDto : resultRebates) {
						List<RebatesConfigDto> collect = rebatesConfigDtoList.stream()
								.filter(t -> t.getGameCategoryId().equals(rebatesConfigDto.getGameCategoryId()))
								.collect(Collectors.toList());
						if (CollectionUtil.isNotEmpty(collect)) {
							rebatesConfigDto.setRebates(collect.get(0).getRebates());
						}
					}
				}

				PlatformConfigAllDto platformConfigAllDto = new PlatformConfigAllDto();
				platformConfigAllDto.setConfigName(sysParameterListVO.getParamName());
				platformConfigAllDto.setUpdateUser(sysParameterListVO.getUpdateUser());
				platformConfigAllDto.setUpdateTime(sysParameterListVO.getUpdateTime());
				platformConfigAllDto.setConfiCode(sysParameterListVO.getParamCode());
				platformConfigAllDto.setContext(JSON.toJSONString(resultRebates));
				result.add(platformConfigAllDto);
				continue;
			}

			PlatformConfigAllDto platformConfigAllDto = new PlatformConfigAllDto();
			platformConfigAllDto.setConfigName(sysParameterListVO.getParamName());
			platformConfigAllDto.setUpdateUser(sysParameterListVO.getUpdateUser());
			platformConfigAllDto.setUpdateTime(sysParameterListVO.getUpdateTime());
			platformConfigAllDto.setConfiCode(sysParameterListVO.getParamCode());
			platformConfigAllDto.setContext(sysParameterListVO.getParamValue());
			result.add(platformConfigAllDto);
		}
		return result;
	}

	public void update(PlatformConfigAllDto req, LoginUser loginUser) {
		SysParameter byCode = sysParameterService.getByCode(req.getConfiCode());
		if (byCode == null) {
			throw new BusinessException("code没有对应的配置");
		}
		byCode.setUpdateUser(loginUser.getAccLogin());
		byCode.setUpdateTime(new Date());
		byCode.setParamValue(req.getContext());
		sysParameterService.updateSysParameter(byCode);
	}
	
	
}
