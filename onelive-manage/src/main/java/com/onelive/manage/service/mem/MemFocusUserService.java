package com.onelive.manage.service.mem;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.mem.FocusUserVo;
import com.onelive.common.mybatis.entity.MemFocusUser;

/**
 * <p>
 *  			关注服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-18
 */
public interface MemFocusUserService extends IService<MemFocusUser> {

	/**
	 * 	关注列表多条件查询
	 * @param focusUserVo
	 * @return
	 */
	List<FocusUserVo> getList(FocusUserVo focusUserVo);

}
