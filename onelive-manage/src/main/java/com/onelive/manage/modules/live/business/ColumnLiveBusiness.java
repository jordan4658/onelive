package com.onelive.manage.modules.live.business;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.index.LiveColumnDto;
import com.onelive.common.mybatis.entity.LiveColumn;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.index.LiveColumnService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;

@Component
public class ColumnLiveBusiness {

	@Autowired
	private LiveColumnService liveColumnService;

	public PageInfo<LiveColumnDto> getList(LiveColumnDto liveColumn) {
		PageHelper.startPage(liveColumn.getPageNum(), liveColumn.getPageSize());
		List<LiveColumn> list = liveColumnService.list();
		return new PageInfo<>(BeanCopyUtil.copyCollection(list, LiveColumnDto.class));
	}

	public void save(LiveColumnDto liveColumnDto) {
		LiveColumn liveColumn = new LiveColumn();
		BeanUtils.copyProperties(liveColumnDto, liveColumn);
		liveColumnService.save(liveColumn);
		SysBusinessRedisUtils.dels(RedisCacheableKeys.LIVE_COLUMN);
	}

	public void update(LiveColumnDto liveColumnDto) {

		if (liveColumnDto.getColumnId() == null) {
			throw new BusinessException("主键不能为空");
		}
		LiveColumn liveColumn = liveColumnService.getById(liveColumnDto.getColumnId());
		if (liveColumn == null) {
			throw new BusinessException("找不到对应的栏目信息");
		}
		BeanUtils.copyProperties(liveColumnDto, liveColumn);
		liveColumnService.updateById(liveColumn);
		SysBusinessRedisUtils.dels(RedisCacheableKeys.LIVE_COLUMN);
	}
	
	public void enableOrDisable(LiveColumnDto liveColumnDto) {
		if (liveColumnDto.getColumnId() == null) {
			throw new BusinessException("主键不能为空");
		}
		LiveColumn liveColumn = liveColumnService.getById(liveColumnDto.getColumnId());
		if (liveColumn == null) {
			throw new BusinessException("找不到对应的栏目信息");
		}
		liveColumn.setIsOpen(liveColumnDto.getIsOpen());
		liveColumnService.updateById(liveColumn);
		SysBusinessRedisUtils.del(RedisCacheableKeys.LIVE_COLUMN);
	}

}
