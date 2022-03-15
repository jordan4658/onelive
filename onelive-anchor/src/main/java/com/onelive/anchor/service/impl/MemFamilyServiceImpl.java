package com.onelive.anchor.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.MemFamilyService;
import com.onelive.common.mybatis.entity.MemFamily;
import com.onelive.common.mybatis.mapper.master.mem.MemFamilyMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemFamilyMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;

/**
 * <p>
 * 家族 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class MemFamilyServiceImpl extends ServiceImpl<MemFamilyMapper, MemFamily> implements MemFamilyService {

	@Resource
	private SlaveMemUserMapper slaveMemUserMapper;

	@Resource
	private SlaveMemFamilyMapper slaveMemFamilyMapper;

	@Override
	public MemFamily queryByUserId(Long userId) {
		QueryWrapper<MemFamily> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemFamily::getUserId, userId);
		return slaveMemFamilyMapper.selectOne(queryWrapper);
	}

}
