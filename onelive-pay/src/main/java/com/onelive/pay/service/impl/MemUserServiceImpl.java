package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.pay.service.MemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
@Service
public class MemUserServiceImpl extends ServiceImpl<MemUserMapper, MemUser> implements MemUserService {

	@Resource
	private SlaveMemUserMapper slaveMemUserMapper;



	@Override
	public MemUser queryById(Long userId) {
		return slaveMemUserMapper.selectById(userId);
	}

}
