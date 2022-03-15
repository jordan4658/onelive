package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sys.appversion.SysAppVersionListReq;
import com.onelive.common.mybatis.entity.SysAppVersion;
import com.onelive.common.mybatis.mapper.master.sys.SysAppVersionMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysAppVersionMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.sys.SysAppVersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * app版本管理 服务实现类
 * </p>
 */
@Service
public class SysAppVersionServiceImpl extends ServiceImpl<SysAppVersionMapper, SysAppVersion> implements SysAppVersionService {

	@Resource
	private SlaveSysAppVersionMapper slaveSysAppVersionMapper;
	/* （非 Javadoc）
	 * @see com.onelive.manage.service.sys.SysAdvNoticeService#getAllList(com.onelive.common.model.req.sys.SysAdvNoticeQueryReq)
	 */
	@Override
	public PageInfo<SysAppVersion> getAllList(SysAppVersionListReq req) {
		 LambdaQueryWrapper<SysAppVersion> wrapper = Wrappers.<SysAppVersion>lambdaQuery()
	                .eq(SysAppVersion::getMerchantCode, LoginInfoUtil.getMerchantCode());
	        return PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> slaveSysAppVersionMapper.selectList(wrapper));
	}
}
