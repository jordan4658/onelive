package com.onelive.api.service.mem;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.live.FansUserVO;
import com.onelive.common.mybatis.entity.MemFocusUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-18
 */
public interface MemFocusUserService extends IService<MemFocusUser> {

    /**
     *	 当前用户是否关注focusUserId的主播
     * @param focusUserId
     * @return
     */
    Boolean isExistFocus(Long focusUserId);

	/**
	 * 	查询用户的关注主播信息
	 * 
	 * @param userId
	 * @return
	 */
	List<FansUserVO> focusList(Long userId);

	/**
	 * 根据主播id获取粉丝列表
	 * @param userId
	 * @return
	 */
	List<FansUserVO> fansList(Long focusId);

	/**
	 * 	当前主播的粉丝数量
	 * @param userId
	 * @return
	 */
	int fansCount(Long userId);

}
