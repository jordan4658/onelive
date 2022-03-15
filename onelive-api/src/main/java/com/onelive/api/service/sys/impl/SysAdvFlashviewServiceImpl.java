package com.onelive.api.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.sys.SysAdvFlashviewService;
import com.onelive.common.mybatis.entity.SysAdvFlashview;
import com.onelive.common.mybatis.entity.SysAdvFlashviewLang;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvFlashviewMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysAdvFlashviewMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.sys.SystemUtil;

/**
 * <p>
 *		 首页轮播表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
@Service
public class SysAdvFlashviewServiceImpl extends ServiceImpl<SysAdvFlashviewMapper, SysAdvFlashview>
		implements SysAdvFlashviewService {

	@Resource
	private SlaveSysAdvFlashviewMapper slaveSysAdvFlashviewMapper;

	/**
	 * 	TODO 缓存
	 */
	@Override
	public List<SysAdvFlashviewLang> getAdvByType(String flashviewType) {
		return slaveSysAdvFlashviewMapper.getAdvByType(flashviewType, LoginInfoUtil.getLang(), SystemUtil.getLangTime());
	}

}
