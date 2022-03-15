package com.onelive.anchor.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.LiveStudioListService;
import com.onelive.anchor.service.MemFamilyService;
import com.onelive.anchor.service.MemUserAnchorService;
import com.onelive.anchor.service.MemUserService;
import com.onelive.anchor.util.AnchorBusinessRedisUtils;
import com.onelive.common.constants.business.LiveConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.mybatis.mapper.master.mem.MemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;

import cn.hutool.core.bean.BeanUtil;

/**
 * <p>
 * 主播服务实现类
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
	private LiveStudioListService liveStudioListService;

	@Override
	public MemUserAnchor getInfoByStudioId(Integer studioId) {
		LiveStudioList room = slaveLiveStudioListMapper.selectById(studioId);
		return getInfoByUserId(room.getUserId());
	}

	@Override
	public MemUserAnchor getInfoByUserId(Long userId) {
		return slaveMemUserAnchorMapper.getInfoByUserId(userId);
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
		this.save(memUserAnchor);
		
		// 3.初始化直播表
		LiveStudioList liveStudioList = new LiveStudioList();
		String studioNum = String.valueOf(
				AnchorBusinessRedisUtils.createStudioNum(RedisKeys.LIVE_STUDIO_NUM, LiveConstants.LIVE_STUDIO_START_NUM));
		liveStudioList.setStudioNum(studioNum.toString());
		liveStudioList.setUserId(saveUserAnchor.getId());
		liveStudioList.setStudioStatus(0);
		liveStudioList.setCountryCode(req.getCountryCode());
		liveStudioListService.save(liveStudioList);
		return 1;
	}

	@Override
	public MemUserAnchor getFocusAward(Long focusUserId) {
		return slaveMemUserAnchorMapper.getFocusAward(focusUserId);
	}

}
