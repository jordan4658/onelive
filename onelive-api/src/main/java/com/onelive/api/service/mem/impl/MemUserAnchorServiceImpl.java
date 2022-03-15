package com.onelive.api.service.mem.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemFamilyService;
import com.onelive.api.service.mem.MemFocusUserService;
import com.onelive.api.service.mem.MemUserAnchorService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;
import com.onelive.common.model.vo.live.LiveAnchorVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.mybatis.mapper.master.mem.MemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.upload.AWSS3Util;

import cn.hutool.core.bean.BeanUtil;

/**
 * <p>
 * 		主播服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-18
 */
@Service
public class MemUserAnchorServiceImpl extends ServiceImpl<MemUserAnchorMapper, MemUserAnchor>
		implements MemUserAnchorService {

	@Resource
	private SlaveMemUserAnchorMapper slaveMemUserAnchorMapper;
	
	@Resource
	private SlaveLiveStudioListMapper slaveLiveStudioListMapper;

	@Resource
	private MemUserService memUserService;
	
    @Resource
    private SlaveMemUserMapper slaveMemUserMapper;
    
    @Resource
    private MemFamilyService memFamilyService;
    
    @Resource
    private MemFocusUserService memFocusUserService;

	@Override
	public MemUserAnchor getInfoByStudioId(Integer studioId) {
		LiveStudioList room = slaveLiveStudioListMapper.selectById(studioId);
		return getInfoByUserId(room.getUserId());
	}

	@Override
	public MemUserAnchor getInfoByUserId(Long userId) {
		QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemUserAnchor::getUserId, userId);
		queryWrapper.lambda().eq(MemUserAnchor::getMerchantCode, LoginInfoUtil.getMerchantCode()).last("limit 1 ");
		return slaveMemUserAnchorMapper.selectOne(queryWrapper);
	}

	@Override
	public LiveAnchorDetailVO selectLiveAnchorDetail(Long hostId) {
		return slaveMemUserAnchorMapper.selectLiveAnchorDetail(hostId);
	}

	@Override
	@Transactional
	public int save(MemUserAnchorSaveReq req) throws Exception {
		// 1.调用注册用户接口,保存到mem_user表
		UserAnchorDTO userAnchorDTO = new UserAnchorDTO();
		BeanUtil.copyProperties(req, userAnchorDTO);
		userAnchorDTO.setUserType(2);
		MemUser saveUserAnchor = memUserService.saveUserAnchor(userAnchorDTO);

		MemUserAnchor memUserAnchor = new MemUserAnchor();
		// 2.保存到mem_user_anchor表
		BeanUtil.copyProperties(req, memUserAnchor);
		memUserAnchor.setUserId(saveUserAnchor.getId());
		memUserAnchor.setFamilyId(LoginInfoUtil.getUserId());
		this.save(memUserAnchor);
		return 1;
	}

	@Override
	public MemUserAnchor getFocusAward(Long focusUserId) {
		return slaveMemUserAnchorMapper.getFocusAward(focusUserId);
	}

	@Override
	public LiveAnchorVO getAnchorInfoByStudioNum(String studioNum) {
		LiveAnchorVO vo = slaveMemUserAnchorMapper.getAnchorInfoByStudioNum(studioNum);
		vo.setAvatar(vo.getAvatar());
		// 获取当前用户是否已关注了用户
		vo.setIsFocus(memFocusUserService.isExistFocus(vo.getUserId()));
		vo.setAvatar(AWSS3Util.getAbsoluteUrl(vo.getAvatar()));
		return vo;
	}

	@Override
	public LiveAnchorDetailVO getAnchorInfoDetailByStudioNum(String studioNum) {
		LiveAnchorDetailVO vo = slaveMemUserAnchorMapper.getAnchorInfoDetailByStudioNum(studioNum);
		vo.setAvatar(vo.getAvatar());
		// 获取当前用户是否已关注了用户
		vo.setIsFocus(memFocusUserService.isExistFocus(vo.getUserId()));
		vo.setAvatar(AWSS3Util.getAbsoluteUrl(vo.getAvatar()));
		return vo;
	}

	@Override
	public int updateAnchorFocuseTotal(Long userId, BigDecimal focusAward) {
		return slaveMemUserAnchorMapper.updateAnchorFocuseTotal(userId, focusAward);
	}


}
