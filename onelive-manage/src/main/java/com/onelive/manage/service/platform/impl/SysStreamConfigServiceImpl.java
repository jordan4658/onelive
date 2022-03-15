package com.onelive.manage.service.platform.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysStreamConfig;
import com.onelive.common.mybatis.mapper.master.platform.SysStreamConfigMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveSysStreamConfigMapper;
import com.onelive.manage.service.platform.SysStreamConfigService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-01
 */
@Service
public class SysStreamConfigServiceImpl extends ServiceImpl<SysStreamConfigMapper, SysStreamConfig> implements SysStreamConfigService {

	@Resource
	private SlaveSysStreamConfigMapper slaveSysStreamConfigMapper;
	
	@Override
	public SysStreamConfig getUse() {
		QueryWrapper<SysStreamConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SysStreamConfig :: getStatus, true).last("LIMIT 1");
		return slaveSysStreamConfigMapper.selectOne(queryWrapper);
	}

}
