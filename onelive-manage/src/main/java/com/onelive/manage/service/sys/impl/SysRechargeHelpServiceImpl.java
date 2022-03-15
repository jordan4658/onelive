package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.mybatis.entity.SysHelpInfo;
import com.onelive.common.mybatis.entity.SysHelpLang;
import com.onelive.common.mybatis.entity.SysRechargeHelpLang;
import com.onelive.common.mybatis.mapper.master.sys.SysHelpLangMapper;
import com.onelive.common.mybatis.mapper.master.sys.SysRechargeHelpLangMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysRechargeHelp;
import com.onelive.common.mybatis.mapper.master.sys.SysRechargeHelpMapper;
import com.onelive.manage.service.sys.SysRechargeHelpService;

import javax.annotation.Resource;

/**
 * <p>
 * 充值说明表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Service
public class SysRechargeHelpServiceImpl extends ServiceImpl<SysRechargeHelpMapper, SysRechargeHelp> implements SysRechargeHelpService {

	@Resource
	private SysRechargeHelpLangMapper sysRechargeHelpLangMapper;

	@Override
	public void updateStatus(Long id, String account) {
		// TODO 
		 UpdateWrapper<SysRechargeHelp> wrapper = new UpdateWrapper<>();
	        wrapper.lambda().set(SysRechargeHelp::getIsDelete, true)
	                .set(SysRechargeHelp::getUpdateUser, account)
	                .eq(SysRechargeHelp::getId, id);
	        this.update(null, wrapper);



		//删除头部信息
		QueryWrapper<SysRechargeHelp> deleteWrapper = new QueryWrapper<>();
		deleteWrapper.lambda().eq(SysRechargeHelp::getId,id);
		this.baseMapper.delete(deleteWrapper);

		//删除明细信息
		QueryWrapper<SysRechargeHelpLang> deleteDetailWrapper = new QueryWrapper<>();
		deleteDetailWrapper.lambda().eq(SysRechargeHelpLang::getHelpId,id);
		this.sysRechargeHelpLangMapper.delete(deleteDetailWrapper);

	}
}
