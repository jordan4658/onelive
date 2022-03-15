package com.onelive.api.service.live;

import java.util.List;

import com.onelive.common.model.vo.live.LiveUserDetailVO;

/**
 * 直播间内相关接口
 *
 * @author maomao
 */
public interface RoomService {

	List<LiveUserDetailVO> onlineUsers(String studioNum);


}
