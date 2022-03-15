package com.onelive.api.service.mem.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemFocusUserService;
import com.onelive.common.model.vo.live.FansUserVO;
import com.onelive.common.mybatis.entity.MemFocusUser;
import com.onelive.common.mybatis.mapper.master.mem.MemFocusUserMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemFocusUserMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;

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
	private SlaveMemFocusUserMapper slaveMemFocusUserMapper;

	@Override
	public Boolean isExistFocus(Long focusUserId) {
		QueryWrapper<MemFocusUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemFocusUser::getUserId, LoginInfoUtil.getUserId());
		queryWrapper.lambda().eq(MemFocusUser::getFocusId, focusUserId);
		queryWrapper.lambda().eq(MemFocusUser::getIsFocus, true);
		queryWrapper.lambda().eq(MemFocusUser::getMerchantCode, LoginInfoUtil.getMerchantCode());
		List<MemFocusUser> list = slaveMemFocusUserMapper.selectList(queryWrapper);
		if (CollectionUtils.isNotEmpty(list)) {
			return true;
		}
		return false;
	}

	@Override
	public List<FansUserVO> focusList(Long userId) {
		return slaveMemFocusUserMapper.focusList(userId);
	}

	@Override
	public List<FansUserVO> fansList(Long focusId) {
		return slaveMemFocusUserMapper.fansList(focusId);
	}

	@Override
	public int fansCount(Long userId) {
		return slaveMemFocusUserMapper.fansCount(userId);
	}
}
