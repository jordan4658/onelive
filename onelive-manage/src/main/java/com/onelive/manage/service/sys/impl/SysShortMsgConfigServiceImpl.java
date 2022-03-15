package com.onelive.manage.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.SysShortMsgConfig;
import com.onelive.common.mybatis.mapper.master.sys.SysShortMsgConfigMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysShortMsgConfigMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.sys.SysShortMsgConfigService;

/**
 * <p>
 * 短信配置 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-03
 */
@Service
public class SysShortMsgConfigServiceImpl extends ServiceImpl<SysShortMsgConfigMapper, SysShortMsgConfig>
		implements SysShortMsgConfigService {
	@Resource
	private SlaveSysShortMsgConfigMapper slaveSysShortMsgConfigMapper;

	@Override
	public PageInfo<SysShortMsgConfig> getList(Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<SysShortMsgConfig> wrapper = Wrappers.<SysShortMsgConfig>lambdaQuery()
				.eq(SysShortMsgConfig::getMerchantCode, LoginInfoUtil.getMerchantCode());
		
		return PageHelper.startPage(pageNum, pageSize)
				.doSelectPageInfo(() -> slaveSysShortMsgConfigMapper.selectList(wrapper));
	}

	@Override
	public void delete(Long id, String account) {
		UpdateWrapper<SysShortMsgConfig> wrapper = new UpdateWrapper<>();
		wrapper.lambda().set(SysShortMsgConfig::getUpdateUser, account).eq(SysShortMsgConfig::getId, id);
		this.update(null, wrapper);

	}
}
