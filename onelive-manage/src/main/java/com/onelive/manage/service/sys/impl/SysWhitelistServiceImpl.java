package com.onelive.manage.service.sys.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.SysWhitelist;
import com.onelive.common.mybatis.mapper.master.sys.SysWhitelistMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysWhitelistMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.sys.SysWhitelistService;

/**
 * <p>
 * 白名单 服务实现类
 * </p>
 */
@Service
public class SysWhitelistServiceImpl extends ServiceImpl<SysWhitelistMapper, SysWhitelist> implements SysWhitelistService {

	@Resource
	private SlaveSysWhitelistMapper slaveSysWhitelistMapper;
	
	/* （非 Javadoc）
	 * @see com.onelive.manage.service.sys.SysWhitelistService#getList(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageInfo<SysWhitelist> getList(String keyword, Integer pageNum, Integer pageSize) {
		// TODO 
		 LambdaQueryWrapper<SysWhitelist> wrapper = Wrappers.<SysWhitelist>lambdaQuery()
				 .eq(SysWhitelist::getIsDelete, false)   
				 .eq(SysWhitelist::getMerchantCode, LoginInfoUtil.getMerchantCode());
         if (StringUtils.isNotEmpty(keyword)) {
        	 wrapper.like(SysWhitelist::getIp, keyword);
		 }
	        return PageHelper.startPage(pageNum,pageSize).doSelectPageInfo(() -> slaveSysWhitelistMapper.selectList(wrapper));
	}

	/* （非 Javadoc）
	 * @see com.onelive.manage.service.sys.SysWhitelistService#delete(java.lang.Long, java.lang.String)
	 */
	@Override
	public void delete(Long id, String account) {
	    UpdateWrapper<SysWhitelist> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(SysWhitelist::getIsDelete, true)
                .set(SysWhitelist::getUpdateUser, account)
                .eq(SysWhitelist::getId, id);
        this.update(null, wrapper);

	}

}
