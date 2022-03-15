package com.onelive.api.service.sys.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.sys.SysAdvNoticeService;
import com.onelive.common.model.dto.sys.SysAdvNoticeDto;
import com.onelive.common.mybatis.entity.SysAdvNotice;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvNoticeMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysAdvNoticeMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.sys.SystemUtil;

/**
 * <p>
 * 广告公告表 服务实现类
 * </p>
 *
 * @since 2021-10-19
 */
@Service
public class SysAdvNoticeServiceImpl extends ServiceImpl<SysAdvNoticeMapper, SysAdvNotice>
		implements SysAdvNoticeService {

	@Resource
	private SlaveSysAdvNoticeMapper slaveSysAdvNoticeMapper;

	@Override
	public SysAdvNoticeDto selectOneByType(Integer type, String lang) {
		lang = StringUtils.isEmpty(lang) ? "zh_CN" : lang;
		return slaveSysAdvNoticeMapper.selectOneByType(type, lang, LoginInfoUtil.getMerchantCode(), SystemUtil.getLangTime());
	}

}
