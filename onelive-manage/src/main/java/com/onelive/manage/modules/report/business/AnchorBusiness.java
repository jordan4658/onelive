package com.onelive.manage.modules.report.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.report.AnchorReportDto;
import com.onelive.manage.service.mem.MemUserAnchorService;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AnchorBusiness {
	
	@Resource
	private MemUserAnchorService memUserAnchorService;
	
	/**
	 * 
	 *  主播报表分页查询
	 * @param anchorReportDto
	 * @return
	 */
	public PageInfo<AnchorReportDto> getList(AnchorReportDto anchorReportDto) {
		PageHelper.startPage(anchorReportDto.getPageNum(), anchorReportDto.getPageSize());
		List<AnchorReportDto> list = memUserAnchorService.getReportList(anchorReportDto);
		return new PageInfo<>(list);
	}
}
