package com.onelive.manage.service.live.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.platform.LiveIncomeDetailDto;
import com.onelive.common.model.vo.live.LiveLogVO;
import com.onelive.common.mybatis.entity.LiveStudioLog;
import com.onelive.common.mybatis.mapper.master.live.LiveStudioLogMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioLogMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.live.LiveStudioLogService;

@Service
public class LiveStudioLogImpl extends ServiceImpl<LiveStudioLogMapper, LiveStudioLog> implements LiveStudioLogService {

	@Autowired
	private SlaveLiveStudioLogMapper slaveLiveStudioLogMapper;

	@Override
	public List<LiveLogVO> getList(LiveLogVO param) {
		param.setLang(LoginInfoUtil.getLang());
		return slaveLiveStudioLogMapper.getList(param);
	}

	@Override
	public LiveIncomeDetailDto detail(LiveIncomeDetailDto param) {
		return slaveLiveStudioLogMapper.detail(param);
	}

	
	
}
