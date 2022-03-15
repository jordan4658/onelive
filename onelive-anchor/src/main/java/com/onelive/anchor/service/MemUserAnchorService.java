package com.onelive.anchor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;
import com.onelive.common.mybatis.entity.MemUserAnchor;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-18
 */
public interface MemUserAnchorService extends IService<MemUserAnchor> {

	/**
	 * 通过房间id获取主播信息
	 * 
	 * @param studioId
	 * @return
	 */
	MemUserAnchor getInfoByStudioId(Integer studioId);

	/**
	 * 通过用户id获取主播信息
	 * 
	 * @param userId
	 * @return
	 */
	MemUserAnchor getInfoByUserId(Long userId);

	/**
	 * 根据主播userid 查询当前直播的详情 包括昵称，开播地址，头像等
	 * 
	 * @param hostId
	 * @return
	 */
	LiveAnchorDetailVO selectLiveAnchorDetail(Long hostId);

	/**
	 * 创建主播
	 * 
	 * @param memUserAnchorReq
	 * @return
	 */
	int save(MemUserAnchorSaveReq memUserAnchorReq) throws Exception;

	/**
	 * 查询主播的被关注奖励金额
	 * 
	 * @param focusUserId
	 * @return
	 */
	MemUserAnchor getFocusAward(Long focusUserId);

}
