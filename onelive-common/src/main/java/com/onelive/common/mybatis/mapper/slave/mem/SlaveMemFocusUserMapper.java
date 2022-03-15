package com.onelive.common.mybatis.mapper.slave.mem;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.vo.live.FansUserVO;
import com.onelive.common.model.vo.mem.FocusUserVo;
import com.onelive.common.mybatis.entity.MemFocusUser;

/**
 * <p>
 *  	用户关注查询Mapper 接口
 * </p>
 *
 * @author mao
 * @since 2021-10-18
 */
public interface SlaveMemFocusUserMapper extends BaseMapper<MemFocusUser> {

	/**
	 * 	查询用户的关注主播信息
	 * 
	 * @param userId
	 * @return
	 */
	List<FansUserVO> focusList(Long userId);

	/**
	 * 主播的粉丝列表
	 * 
	 * @param focusId
	 * @return
	 */
	List<FansUserVO> fansList(Long focusId);

	
	/**
	 * 	后台多条件查询关注列表
	 * @param focusUserVo
	 * @return
	 */
	List<FocusUserVo> getList(FocusUserVo focusUserVo);

	/**
	 *	 当前主播的粉丝数量
	 * @param focusId
	 * @return
	 */
	int fansCount(Long focusId);

}
