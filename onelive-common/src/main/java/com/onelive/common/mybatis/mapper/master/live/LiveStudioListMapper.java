package com.onelive.common.mybatis.mapper.master.live;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.LiveStudioList;

public interface LiveStudioListMapper extends BaseMapper<LiveStudioList> {
	
	Integer saveOrUpdate(LiveStudioList liveStudioList);

	int breakShow(Long userId);

}