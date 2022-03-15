package com.onelive.manage.modules.live.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.platform.LiveGiftLogDetailDto;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.platform.LiveGiftLogService;

@Component
public class LiveGiftLogBusiness {

	@Autowired
	private LiveGiftLogService liveGiftLogService;

	public PageInfo<LiveGiftLogDetailDto> getList(LiveGiftLogDetailDto param) {
		param.setMerchantCode(LoginInfoUtil.getMerchantCode());
		return liveGiftLogService.getList(param);
	}

}
