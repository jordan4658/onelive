package com.onelive.manage.service.live;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.platform.LiveIncomeDetailDto;
import com.onelive.common.model.vo.live.LiveLogVO;
import com.onelive.common.mybatis.entity.LiveStudioLog;


public interface LiveStudioLogService extends IService<LiveStudioLog> {

	/**
	 * 	查询 直播历史
	 * 
	 * @param param
	 * @return
	 */
	List<LiveLogVO> getList(LiveLogVO param);

	/**
	 * 
	 * 	单场直播收入详情
	 * @param param
	 * @return
	 */
	LiveIncomeDetailDto detail(LiveIncomeDetailDto param);

}
