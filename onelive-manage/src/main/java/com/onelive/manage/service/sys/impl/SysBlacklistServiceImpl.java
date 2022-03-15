package com.onelive.manage.service.sys.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.SysBlacklist;
import com.onelive.common.mybatis.mapper.master.sys.SysBlacklistMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysBlacklistMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.sys.SysBlacklistService;

/**
 * <p>
 * 用户黑名单 服务实现类
 * </p>
 */
@Service
public class SysBlacklistServiceImpl extends ServiceImpl<SysBlacklistMapper, SysBlacklist> implements SysBlacklistService {
	@Resource
	private SlaveSysBlacklistMapper slaveSysBlacklistMapper;
	
	/* （非 Javadoc）
	 * @see com.onelive.manage.service.sys.SysWhitelistService#getList(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageInfo<SysBlacklist> getList(String keyword, Integer pageNum, Integer pageSize) {
		// TODO 
		 LambdaQueryWrapper<SysBlacklist> wrapper = Wrappers.<SysBlacklist>lambdaQuery()
				 .eq(SysBlacklist::getIsDelete, false)   
				 .eq(SysBlacklist::getMerchantCode, LoginInfoUtil.getMerchantCode());
         if (StringUtils.isNotEmpty(keyword)) {
        	 wrapper.like(SysBlacklist::getIp, keyword);
		 }
	        return PageHelper.startPage(pageNum,pageSize).doSelectPageInfo(() -> slaveSysBlacklistMapper.selectList(wrapper));
	}
}
