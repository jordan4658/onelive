package com.onelive.manage.service.mem.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.mem.FocusUserVo;
import com.onelive.common.mybatis.entity.MemFocusUser;
import com.onelive.common.mybatis.mapper.master.mem.MemFocusUserMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemFocusUserMapper;
import com.onelive.manage.service.mem.MemFocusUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-18
 */
@Service
public class MemFocusUserServiceImpl extends ServiceImpl<MemFocusUserMapper, MemFocusUser>
		implements MemFocusUserService {

	@Resource
	SlaveMemFocusUserMapper slaveMemFocusUserMapper;

	@Override
	public List<FocusUserVo> getList(FocusUserVo focusUserVo) {
		return slaveMemFocusUserMapper.getList(focusUserVo);
	}

}
