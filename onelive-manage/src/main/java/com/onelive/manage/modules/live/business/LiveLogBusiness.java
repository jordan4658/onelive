package com.onelive.manage.modules.live.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.platform.LiveIncomeDetailDto;
import com.onelive.common.model.vo.live.LiveLogVO;
import com.onelive.manage.service.live.LiveStudioLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LiveLogBusiness {

	@Autowired
	private LiveStudioLogService liveStudioListService;

	public PageInfo<LiveLogVO> getList(LiveLogVO param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<LiveLogVO> list = liveStudioListService.getList(param);
		return new PageInfo<LiveLogVO>(list);
	}

	public LiveIncomeDetailDto detail(LiveIncomeDetailDto param) {
		if (param.getLogId() == null) {
			throw  new BusinessException("直播记录id不可为空！");
		}
		return liveStudioListService.detail(param);
	}

}
