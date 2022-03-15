package com.onelive.api.modules.live.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.api.service.mem.MemFocusUserService;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.dto.platform.MemFocusUserRemindDto;
import com.onelive.common.model.req.mem.MemUserIdReq;
import com.onelive.common.model.vo.live.FansUserVO;
import com.onelive.common.mybatis.entity.MemFocusUser;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.upload.AWSS3Util;

@Component
public class FocusBusiness extends BaseController {

	@Autowired
	private MemFocusUserService memFocusUserService;

	/**
	 * 用户的关注列表
	 * 
	 * @return
	 */
	public PageInfo<FansUserVO> focusList(MemUserIdReq memUserIdReq) {
		Long userId = memUserIdReq.getUserId() == null ? LoginInfoUtil.getUserId() : memUserIdReq.getUserId();
		PageHelper.startPage(memUserIdReq.getPageNum(), memUserIdReq.getPageSize());
		List<FansUserVO> result = memFocusUserService.focusList(userId);
		return new PageInfo<FansUserVO>(result);
	}

	/**
	 * 用户设置主播开播提醒
	 * 
	 * @param memFocusUserRemindDto
	 * @return
	 */
	public Boolean setStartedToRemind(MemFocusUserRemindDto memFocusUserRemindDto) {
		QueryWrapper<MemFocusUser> queryWrapperFocus = new QueryWrapper<>();
		queryWrapperFocus.lambda().eq(MemFocusUser :: getUserId, LoginInfoUtil.getUserId());
		queryWrapperFocus.lambda().eq(MemFocusUser :: getFocusId, memFocusUserRemindDto.getFocusId());
		MemFocusUser one = memFocusUserService.getOne(queryWrapperFocus);
		if (one != null && memFocusUserRemindDto.getIsRemind() != one.getIsRemind()) {
			one.setIsRemind(memFocusUserRemindDto.getIsRemind());
			memFocusUserService.updateById(one);
		}
		return true;
	}

	/**
	 * 
	 * 根据主播id查询关注主播的用户信息列表
	 * 
	 * @param memUserIdReq
	 * @return
	 */
	public PageInfo<FansUserVO> fansList(MemUserIdReq memUserIdReq) {
		PageHelper.startPage(memUserIdReq.getPageNum(), memUserIdReq.getPageSize());
		// 关注用户列表
		List<FansUserVO> focusList = memFocusUserService.fansList(memUserIdReq.getUserId());
		for (FansUserVO fansUserVO : focusList) {
			fansUserVO.setAvatar(AWSS3Util.getAbsoluteUrl(fansUserVO.getAvatar()));
		}
		return new PageInfo<FansUserVO>(focusList);
	}

}
