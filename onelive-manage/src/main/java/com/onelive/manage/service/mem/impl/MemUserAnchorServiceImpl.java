package com.onelive.manage.service.mem.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.LiveConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.dto.report.AnchorReportDto;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorListReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.vo.mem.MemUserAnchorVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.mybatis.mapper.master.mem.MemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.manage.service.live.LiveStudioListService;
import com.onelive.manage.service.mem.MemUserAnchorService;
import com.onelive.manage.service.mem.MemUserService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;

import cn.hutool.core.bean.BeanUtil;

/**
 * 后台主播管理
 */
@Service
public class MemUserAnchorServiceImpl extends ServiceImpl<MemUserAnchorMapper, MemUserAnchor>
		implements MemUserAnchorService {

	@Resource
	private MemUserService memUserService;

	@Resource
	private MemUserMapper memUserMapper;

	@Resource
	private SlaveMemUserAnchorMapper slaveMemUserAnchorMapper;

	@Resource
	private LiveStudioListService liveStudioListService;

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
				SysBusinessRedisUtils.createStudioNum(RedisKeys.LIVE_STUDIO_NUM, LiveConstants.LIVE_STUDIO_START_NUM));
		liveStudioList.setStudioNum(studioNum.toString());
		liveStudioList.setUserId(saveUserAnchor.getId());
		liveStudioList.setStudioStatus(0);
		liveStudioList.setCountryCode(req.getCountryCode());
		liveStudioListService.save(liveStudioList);

		return 1;
	}

	/**
	 * 多条件查询
	 */
	@Override
	public PageInfo<MemUserAnchorVO> getList(MemUserAnchorListReq req) {
		return PageInfoUtil.pageInfo2DTO(PageHelper.startPage(req.getPageNum(), req.getPageSize())
				.doSelectPageInfo(() -> slaveMemUserAnchorMapper.getList(req)), MemUserAnchorVO.class);
	}

	/**
	 * 更新mem_user表 mem_user_anchor表
	 */
	@Override
	public int update(MemUserAnchorSaveReq req) {
		// 1.更新mem_user表
		MemUser memUser = new MemUser();
		BeanUtil.copyProperties(req, memUser);
		memUser.setId(req.getUserId());
		memUser.setUserType(2);
		// 更新密码
		MemUser byId = memUserService.getById(req.getUserId());
		memUser.setPassword(SecurityUtils.MD5SaltEncrypt(req.getPassword(), byId.getSalt()));
		memUserMapper.updateById(memUser);

		// 2.更新mem_user_anchor表
		MemUserAnchor memUserAnchor = new MemUserAnchor();
		BeanUtil.copyProperties(req, memUserAnchor);
		this.updateById(memUserAnchor);
		return 1;
	}

	/**
	 * 主播报表
	 */
	@Override
	public List<AnchorReportDto> getReportList(AnchorReportDto anchorReportDto) {
		return slaveMemUserAnchorMapper.getReportList(anchorReportDto);
	}

	@Override
	public MemUserAnchor getInfoByUserId(Long userId) {
		return slaveMemUserAnchorMapper.getInfoByUserId(userId);
	}

}
