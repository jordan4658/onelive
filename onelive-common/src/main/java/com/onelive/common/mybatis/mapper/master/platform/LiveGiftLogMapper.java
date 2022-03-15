package com.onelive.common.mybatis.mapper.master.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.platform.StudioLogLiveGiftDto;
import com.onelive.common.mybatis.entity.LiveGiftLog;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface LiveGiftLogMapper extends BaseMapper<LiveGiftLog> {

	/**
	 * 	根据直播记录表，查询当场直播收礼总金额
	 * 
	 * @param logId
	 * @return
	 */
	StudioLogLiveGiftDto selectSumBystudioLogId(Integer logId);

}
