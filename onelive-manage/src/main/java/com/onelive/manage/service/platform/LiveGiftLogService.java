package com.onelive.manage.service.platform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.platform.LiveGiftLogDetailDto;
import com.onelive.common.model.dto.report.LiveGifReportDto;
import com.onelive.common.mybatis.entity.LiveGiftLog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface LiveGiftLogService extends IService<LiveGiftLog> {
	
	/**
	 * 	礼物报表统计
	 * @param liveGifReportDto
	 * @return
	 */
	PageInfo<LiveGifReportDto> getReportList(LiveGifReportDto liveGifReportDto);

	/**
	 *  	礼物记录查询,一次赠送一条数据
	 * 
	 * @param param
	 * @return
	 */
	PageInfo<LiveGiftLogDetailDto> getList(LiveGiftLogDetailDto param);
}
