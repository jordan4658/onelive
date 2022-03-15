package com.onelive.api.modules.index.business;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.model.dto.platformConfig.CustomerSericeLangDto;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.StringUtils;

@Component
public class OnlineServiceBusiness {

	@Resource
	private SysParameterService sysParameterService;

	public CustomerSericeLangDto getOnlineService() {
		// 客服存在通用常量表里
		CustomerSericeLangDto customerSericeLangDto = null;
		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.CUSTOMER_SERVICE);
		String paramValue = byCode.getParamValue();
		if (StringUtils.isNotBlank(paramValue)) {
			List<CustomerSericeLangDto> customerSericeLangDtoList = JSON.parseArray(paramValue,CustomerSericeLangDto.class);
			// 当前语言的客服
			List<CustomerSericeLangDto> collect = customerSericeLangDtoList.stream()
					.filter(t -> LoginInfoUtil.getLang().equals(t.getLang())).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(collect)) {
				customerSericeLangDto = collect.get(0);
			}
		}

		return customerSericeLangDto;
	}
	
}
