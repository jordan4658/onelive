package com.onelive.manage.service.platform.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.platform.LiveGiftLogDetailDto;
import com.onelive.common.model.dto.report.LiveGifReportDto;
import com.onelive.common.mybatis.entity.LiveGiftLog;
import com.onelive.common.mybatis.mapper.master.platform.LiveGiftLogMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveGiftLogMapper;
import com.onelive.manage.service.platform.LiveGiftLogService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Service
public class LiveGiftLogServiceImpl extends ServiceImpl<LiveGiftLogMapper, LiveGiftLog> implements LiveGiftLogService {
	@Resource
	private SlaveLiveGiftLogMapper slaveLiveGiftLogMapper;


	@Override
	public PageInfo<LiveGifReportDto> getReportList(LiveGifReportDto liveGifReportDto) {
		PageHelper.startPage(liveGifReportDto.getPageNum(), liveGifReportDto.getPageSize());
		List<LiveGifReportDto> list = slaveLiveGiftLogMapper.getReportList(liveGifReportDto);
		return new PageInfo<>(list);
	}


	@Override
	public PageInfo<LiveGiftLogDetailDto> getList(LiveGiftLogDetailDto param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		return new PageInfo<>(slaveLiveGiftLogMapper.getList(param));
	}


}
