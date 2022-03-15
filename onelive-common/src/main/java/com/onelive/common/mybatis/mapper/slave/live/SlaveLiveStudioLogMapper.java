package com.onelive.common.mybatis.mapper.slave.live;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.platform.LiveIncomeDetailDto;
import com.onelive.common.model.vo.live.LiveHistoryVo;
import com.onelive.common.model.vo.live.LiveLogVO;
import com.onelive.common.mybatis.entity.LiveStudioLog;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SlaveLiveStudioLogMapper extends BaseMapper<LiveStudioLog> {


	LiveStudioLog selectLastByStudioNum(String studioNum);
	
	/**
	 *   根据主播的用户id查询最近一条 直播记录
	 * @param userId
	 * @return
	 */
	LiveStudioLog selectLastByUserId(Long userId);

	/**
	 * 	主播的历史直播记录
	 * @param param
	 * @return
	 */
	List<LiveLogVO> getList(LiveLogVO liveLogVO);

	/**
	 * 
	 * 	单场直播收入详情
	 * @param param
	 * @return
	 */
	LiveIncomeDetailDto detail(LiveIncomeDetailDto param);

	/**
	 * 	主播查看自己的直播记录，最近三十天
	 * @param userId
	 * @return
	 */
	List<LiveHistoryVo> liveHistory(Long userId);

}
