package com.onelive.manage.modules.report.business;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.report.LiveGifReportDto;
import com.onelive.manage.service.platform.LiveGiftLogService;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GiftBusiness {
	
	@Resource
	private LiveGiftLogService liveGiftLogService;
	
	public PageInfo<LiveGifReportDto> getList(LiveGifReportDto liveGifReportDto) {
		return liveGiftLogService.getReportList(liveGifReportDto);
	}
}
