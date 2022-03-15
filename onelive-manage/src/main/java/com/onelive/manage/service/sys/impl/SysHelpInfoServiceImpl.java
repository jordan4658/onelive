package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.mybatis.entity.SysHelpLang;
import com.onelive.common.mybatis.mapper.master.sys.SysHelpLangMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysAdvNotice;
import com.onelive.common.mybatis.entity.SysHelpInfo;
import com.onelive.common.mybatis.mapper.master.sys.SysHelpInfoMapper;
import com.onelive.manage.service.sys.SysHelpInfoService;

import javax.annotation.Resource;

/**
 * <p>
 * 帮助中心表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Service
public class SysHelpInfoServiceImpl extends ServiceImpl<SysHelpInfoMapper, SysHelpInfo> implements SysHelpInfoService {

	@Resource
	private SysHelpLangMapper sysHelpLangMapper;

	@Override
	public void updateStatus(Long id, String account) {
//		// TODO
//		 UpdateWrapper<SysHelpInfo> wrapper = new UpdateWrapper<>();
//	        wrapper.lambda().set(SysHelpInfo::getIsDelete, true)
//	                .set(SysHelpInfo::getUpdateUser, account)
//	                .eq(SysHelpInfo::getId, id);
//	        this.update(null, wrapper);
//


		//删除帮助中心头部信息
		QueryWrapper<SysHelpInfo> deleteWrapper = new QueryWrapper<>();
		deleteWrapper.lambda().eq(SysHelpInfo::getId,id);
		this.baseMapper.delete(deleteWrapper);

		//删除帮助中心明细信息
		QueryWrapper<SysHelpLang> deleteDetailWrapper = new QueryWrapper<>();
		deleteDetailWrapper.lambda().eq(SysHelpLang::getHelpId,id);
		this.sysHelpLangMapper.delete(deleteDetailWrapper);

	}
}
