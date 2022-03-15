package com.onelive.api.modules.sys.business;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onelive.api.service.sys.SysAdvNoticeService;
import com.onelive.common.model.dto.sys.SysAdvNoticeDto;
import com.onelive.common.utils.Login.LoginInfoUtil;

/**
 * @Description: 首页公告务类
 */
@Component
public class SysAdvNoticeBusiness {

	@Resource
	private SysAdvNoticeService service;

	/**
	 * 	根据类型查询公告
	 * @param type
	 * @return
	 */
	public SysAdvNoticeDto getByType(Integer type) {
		return service.selectOneByType(type, LoginInfoUtil.getLang());
	}

}
