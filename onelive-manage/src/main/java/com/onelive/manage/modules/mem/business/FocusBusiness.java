package com.onelive.manage.modules.mem.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.vo.mem.FocusUserVo;
import com.onelive.manage.service.mem.MemFocusUserService;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FocusBusiness {

	@Resource
	private MemFocusUserService memFocusUserService;

	public PageInfo<FocusUserVo> getList(FocusUserVo focusUserVo) {
		PageHelper.startPage(focusUserVo.getPageNum(), focusUserVo.getPageSize());
		List<FocusUserVo> list = memFocusUserService.getList(focusUserVo);
		return new PageInfo<FocusUserVo>(list);
	}

}
