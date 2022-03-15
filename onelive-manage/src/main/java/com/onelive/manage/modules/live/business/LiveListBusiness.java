package com.onelive.manage.modules.live.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.live.LiveBeginForAdminReq;
import com.onelive.common.model.req.live.LiveSortConfigReq;
import com.onelive.common.model.req.live.LiveStudioListReq;
import com.onelive.common.model.vo.live.LiveStudioListDetail;
import com.onelive.common.model.vo.live.LiveStudioListManegeVO;
import com.onelive.common.model.vo.live.LiveStudioSelectVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.live.LiveStudioListService;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class LiveListBusiness {

	@Autowired
	private SysParameterService sysParameterService;
	
	@Autowired
	private LiveStudioListService liveStudioListService;

	@Resource
	private SlaveLiveStudioListMapper slaveLiveStudioListMapper;

	public ResultInfo<PageInfo<LiveStudioListManegeVO>> getList(@ModelAttribute LiveStudioListReq param) {
		param.setMerchantCode(LoginInfoUtil.getMerchantCode());
		PageInfo<LiveStudioListManegeVO> list = liveStudioListService.getList(param);

		return ResultInfo.ok(list);
	}

	public ResultInfo<Boolean> first(Integer studioId, String countryCode, LoginUser admin) {
		
		// 如果当前用户的国家 不等于直播间的国家，不可以操作
		LiveStudioList live = liveStudioListService.getByStudioId(studioId);
		if (admin.getRoleId() != 1 && !live.getCountryCode().equals(countryCode)) {
			throw new BusinessException("只能操作当前登录用户国家对应的直播间");
		}
		
		// 查询已经推荐数量
		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<LiveStudioList>();
		queryWrapper.lambda().eq(LiveStudioList :: getIsFirst, -1);
		queryWrapper.lambda().eq(LiveStudioList :: getStudioStatus, 1);
		queryWrapper.lambda().eq(LiveStudioList :: getMerchantCode, LoginInfoUtil.getMerchantCode());
		List<LiveStudioList> list = liveStudioListService.list(queryWrapper);
		if (list.size() >= 4) {
			throw new BusinessException("推荐直播间最多为4个");
		}
		// 获取推荐中最小的sortNum
		int minSortNum = 0;
		if (!CollectionUtils.isEmpty(list)) {
			list.sort(Comparator.comparing(LiveStudioList::getSortNum));
			minSortNum = list.get(0).getSortNum();
		}
		LiveStudioList liveStudioList = new LiveStudioList();
		liveStudioList.setSortNum(minSortNum - 1);
		liveStudioList.setStudioId(studioId);
		liveStudioList.setUpdateTime(new Date());
		liveStudioList.setUpdatedBy(admin.getAccLogin());
		// 默认值0:取消推荐/取消置顶 -1:推荐优先 2:置底
		liveStudioList.setIsFirst(-1);
		liveStudioListService.updateById(liveStudioList);
		// 推荐直播的缓存
		SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + live.getCountryCode());
		SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + live.getCountryCode());
		return ResultInfo.ok();
	}
	
	public ResultInfo<Boolean> unFirst(Integer studioId, String countryCode, LoginUser admin) {
		// 如果当前用户的国家 不等于直播间的国家id，不可以操作
		LiveStudioList live = liveStudioListService.getByStudioId(studioId);
		if (admin.getRoleId() != 1 && !live.getCountryCode().equals(countryCode)) {
			throw new BusinessException("只能操作当前登录用户国家对应的直播间");
		}
		LiveStudioList liveStudioList = new LiveStudioList();
		liveStudioList.setStudioId(studioId);
		liveStudioList.setIsFirst(0);
		liveStudioListService.updateById(liveStudioList);
		// 推荐直播的缓存
		SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + live.getCountryCode());
		SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + live.getCountryCode());
		return ResultInfo.ok();
	}

	public ResultInfo<Boolean> fixed(Integer studioId, Integer sortNum, LoginUser admin) {
		String countryCode = LoginInfoUtil.getCountryCode();
		// 如果当前用户的国家 不等于直播间的国家id，不可以操作
		LiveStudioList live = liveStudioListService.getByStudioId(studioId);
		if (admin.getRoleId() != 1 && !live.getCountryCode().equals(countryCode)) {
			throw new BusinessException("只能操作当前登录用户国家对应的直播间");
		}
		// 查询当前国家的固定位置的直播间是否存在
		Boolean isExist = liveStudioListService.getThisCountryFixedBySortNum(live.getCountryCode(), sortNum);
		if (isExist) {
			throw new BusinessException("当前国家直播间的固定序号已存在");
		}
		if (sortNum < 1) {
			throw new BusinessException("固定序号不可以小于1");
		}
		LiveStudioList liveStudioList = new LiveStudioList();
		liveStudioList.setStudioId(studioId);
		liveStudioList.setIsFixed(true);
		liveStudioList.setSortNum(sortNum);
		return ResultInfo.ok(liveStudioListService.updateById(liveStudioList));
	}

	public ResultInfo<Boolean> unFixed(Integer studioId, LoginUser admin) {
		String countryCode = LoginInfoUtil.getCountryCode();
		// 如果当前用户的国家 不等于直播间的国家id，不可以操作
		LiveStudioList getone = liveStudioListService.getByStudioId(studioId);
		if (admin.getRoleId() != 1 && !getone.getCountryCode().equals(countryCode)) {
			throw new BusinessException("只能操作当前登录用户国家对应的直播间");
		}
		LiveStudioList liveStudioList = new LiveStudioList();
		liveStudioList.setStudioId(studioId);
		liveStudioList.setIsFixed(false);
		liveStudioList.setSortNum(0);
		return ResultInfo.ok(liveStudioListService.updateById(liveStudioList));
	}

	public ResultInfo<Boolean> bottom(Integer studioId, Boolean isBottom, LoginUser admin) {
		String countryCode = LoginInfoUtil.getCountryCode();
		// 如果当前用户的国家 不等于直播间的国家id，不可以操作
		LiveStudioList getone = liveStudioListService.getByStudioId(studioId);
		if (admin.getRoleId() != 1 && !getone.getCountryCode().equals(countryCode)) {
			throw new BusinessException("只能操作当前登录用户国家对应的直播间");
		}
		// 查询当前开播中最大的排序,+1
		//LiveStudioListVo liveStudioListVo = liveStudioListService.lastSortNum(LoginInfoUtil.getMerchantCode());
		LiveStudioList liveStudioList = new LiveStudioList();
		if (isBottom) {
			liveStudioList.setIsFirst(2);
		} else {
			liveStudioList.setIsFirst(0);
		}
		liveStudioList.setStudioId(studioId);
		liveStudioList.setSortNum(0);
		return ResultInfo.ok(liveStudioListService.updateById(liveStudioList));
	}

	public ResultInfo<Boolean> breakShow(Integer studioId, LoginUser admin) {
		String countryCode = LoginInfoUtil.getCountryCode();
		// 如果当前用户的国家 不等于直播间的国家id，不可以操作
		LiveStudioList live = liveStudioListService.getByStudioId(studioId);
		if(live==null){
			throw new BusinessException(StatusCode.PARAM_ERROR);
		}
		if (admin.getRoleId() != 1 && !live.getCountryCode().equals(countryCode)) {
			throw new BusinessException("只能操作当前登录用户国家对应的直播间");
		}

		return ResultInfo.ok(liveStudioListService.breakShow(live.getUserId()));
	}

	public LiveStudioListDetail detail(Integer studioId) {
		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getStudioId, studioId);
		LiveStudioList liveStudioList = slaveLiveStudioListMapper.selectOne(queryWrapper);

		LiveStudioListDetail liveStudioListDetail = new LiveStudioListDetail();
		liveStudioListDetail.setSortNum(liveStudioList.getSortNum());
		liveStudioListDetail.setStudioId(studioId);
		liveStudioListDetail.setStudioLivePath(liveStudioList.getStudioLivePath());
		// 获取当前主播的
		Object object = SysBusinessRedisUtils.hGet(WebSocketRedisKeys.anchor_studioNum, liveStudioList.getStudioNum());
		Integer memCountMultiple = object == null ? 0 : (Integer) object;
		liveStudioListDetail.setMemCountMultiple(memCountMultiple);
		return liveStudioListDetail;
	}

	/**
	 * 
	 * 	设置在线人数倍数 
	 * 	用户端查询直播间人数:	人数计算公式 : 50~60 随机数 + (在线人数 * 后台配置的倍数,如果后台没有配置默认 * 1)
	 * 
	 * @param liveStudioListDetail
	 * @return
	 */
	public Boolean setDetail(LiveStudioListDetail liveStudioListDetail) {
		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getStudioId, liveStudioListDetail.getStudioId());
		LiveStudioList liveStudioList = slaveLiveStudioListMapper.selectOne(queryWrapper);
		SysBusinessRedisUtils.hset(WebSocketRedisKeys.anchor_studioNum, liveStudioList.getStudioNum(),
				liveStudioListDetail.getMemCountMultiple());
		return true;
	}

	/**
	 * 	后台指定视频播放，开启直播
	 * 
	 * @param req
	 * @return
	 */
	public Boolean beginVideo(LiveBeginForAdminReq req) {
		req.setMerchantCode(LoginInfoUtil.getMerchantCode());
		return liveStudioListService.beginVideo(req);
	}

	/**
	 * 	推荐栏目直播间排序类型
	 * @param req
	 */
	public void sortConfig(LiveSortConfigReq req) {
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterConstants.LIVE_STUDIO_SORT);
		sysParameter.setParamValue(req.getSortCode().toString());
		sysParameterService.updateSysParameter(sysParameter);
	}

	/**
	 * 	查询推荐栏目直播间排序类型
	 * @return
	 */
	public LiveSortConfigReq getSortConfig() {
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterConstants.LIVE_STUDIO_SORT);
		LiveSortConfigReq liveSortConfigReq = new LiveSortConfigReq();
		liveSortConfigReq.setSortCode(Integer.parseInt(sysParameter.getParamValue()));
		return liveSortConfigReq;
	}

	/**
	 * 查询直播列表-用于选择
	 * @return
	 */
    public List<LiveStudioSelectVO> getSelectList() {
		QueryWrapper<LiveStudioList> queryWrapper=new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getStudioStatus,1);
		List<LiveStudioList> list = liveStudioListService.list();
		if(CollectionUtils.isEmpty(list)){
			return new LinkedList<>();
		}
		return BeanCopyUtil.copyCollection(list,LiveStudioSelectVO.class);
	}
}
