package com.onelive.api.service.live.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.onelive.api.service.live.LiveStudioLogService;
import com.onelive.api.service.live.RoomService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.constants.other.TouristAvatarConstants;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.common.utils.others.UUIDUtils;
import com.onelive.common.utils.pay.RandomUtil;
import com.onelive.common.utils.upload.AWSS3Util;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 直播间内相关接口
 * </p>
 *
 * @author maomao
 */
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
	
	@Resource
	private LiveStudioLogService liveStudioLogService;

	@Resource
	private SlaveLiveStudioListMapper slaveLiveStudioListMapper;

	/**
	 * 		直播间在线用户 ，按照等级排名前50
	 * 
	 */
	@Override
	public List<LiveUserDetailVO> onlineUsers(String studioNum) {
		List<LiveUserDetailVO> result = new ArrayList<>();
		// 在线用户
		Map<String, Object> users = ApiBusinessRedisUtils.hMGet(WebSocketRedisKeys.user_list + studioNum);

		Collection<Object> userValues = users.values();
		for (Object object : userValues) {
			if(object != null && StringUtils.isNotEmpty(object.toString())) {
				try {
					AppLoginUser appLoginUser = JSON.parseObject(object.toString(), AppLoginUser.class);
					LiveUserDetailVO liveUserDetailVO = new LiveUserDetailVO();
					liveUserDetailVO.setUserId(appLoginUser.getId().toString());
					liveUserDetailVO.setAccno(appLoginUser.getAccno());
					liveUserDetailVO.setUserAccount(appLoginUser.getUserAccount());
					liveUserDetailVO.setMerchantCode(appLoginUser.getMerchantCode());
					liveUserDetailVO.setCountryCode(appLoginUser.getCountryCode());
					liveUserDetailVO.setNickName(appLoginUser.getNickName());
					liveUserDetailVO.setPersonalSignature(appLoginUser.getPersonalSignature());
					liveUserDetailVO.setAvatar(appLoginUser.getAvatar());
					liveUserDetailVO.setLevel(appLoginUser.getLevel());
					liveUserDetailVO.setUserType(appLoginUser.getUserType());
					
					result.add(liveUserDetailVO);
				} catch (Exception e) {
					log.error("直播间在线用户 解析失败", e);
				}
			}
		}
		// 等级高的在前面
		result.sort((x, y) -> Integer.compare(y.getLevel(), x.getLevel()));
		if (users.size() < 50) {
			// 总数不大于五十
			int fackNum = 50 - result.size();
			if (fackNum > 0) {
				for (int i = 0; i < fackNum; i++) {
					// 加假的游客数据
					LiveUserDetailVO liveUserDetailVO = new LiveUserDetailVO();
					liveUserDetailVO.setLevel(RandomUtil.getRandomOne(0, 50));
					liveUserDetailVO.setUserId(UUIDUtils.number(6).toString());
					liveUserDetailVO.setNickName("游客" + UUIDUtils.uuid(6).toLowerCase());
					liveUserDetailVO.setAvatar(
							AWSS3Util.getAbsoluteUrl(TouristAvatarConstants.list.get(RandomUtil.getRandomOne(0, 5))));
					liveUserDetailVO.setSex(0);
					liveUserDetailVO.setUserType(1);
					result.add(liveUserDetailVO);
				}
			}
		} else {
			// 截取50个
			result.subList(0, 50);
		}
		return result;
	}

}
