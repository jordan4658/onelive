package com.onelive.anchor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.LiveStudioLogService;
import com.onelive.common.model.vo.live.LiveHistoryVo;
import com.onelive.common.mybatis.entity.LiveStudioLog;
import com.onelive.common.mybatis.mapper.master.live.LiveStudioLogMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioLogMapper;

@Service
public class LiveStudioLogImpl extends ServiceImpl<LiveStudioLogMapper, LiveStudioLog> implements LiveStudioLogService {

	@Autowired
	private SlaveLiveStudioLogMapper slaveLiveStudioLogMapper;

	
	@Override
	public LiveStudioLog selectLastByStudioNum(String studioNum) {
		return slaveLiveStudioLogMapper.selectLastByStudioNum(studioNum);
	}

	@Override
	public LiveStudioLog selectLastByUserId(Long hostId) {
		return slaveLiveStudioLogMapper.selectLastByUserId(hostId);
	}

	@Override
	public List<LiveHistoryVo> liveHistory(Long userId) {
		return slaveLiveStudioLogMapper.liveHistory(userId);
	}
	
}
