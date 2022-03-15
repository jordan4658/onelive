package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.mybatis.entity.SysHelpInfo;
import com.onelive.common.mybatis.entity.SysHelpLang;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysImageInfo;
import com.onelive.common.mybatis.mapper.master.sys.SysImageInfoMapper;
import com.onelive.manage.service.sys.SysImageInfoService;

/**
 * <p>
 * 图片管理表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Service
public class SysImageInfoServiceImpl extends ServiceImpl<SysImageInfoMapper, SysImageInfo> implements SysImageInfoService {

	@Override
	public void updateStatus(Long id, String account) {
		 UpdateWrapper<SysImageInfo> wrapper = new UpdateWrapper<>();
	        wrapper.lambda().set(SysImageInfo::getIsDelete, true)
	                .set(SysImageInfo::getUpdateUser, account)
	                .eq(SysImageInfo::getId, id);
	        this.update(null, wrapper);


	}
}
