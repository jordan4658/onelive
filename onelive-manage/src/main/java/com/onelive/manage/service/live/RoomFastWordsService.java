package com.onelive.manage.service.live;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.live.RoomFastWordsDto;
import com.onelive.common.mybatis.entity.RoomFastWords;

/**
 * <p>
 *  
			直播间快捷回复文字服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-19
 */
public interface RoomFastWordsService extends IService<RoomFastWords> {

	/**
	 * 	后台查询
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<RoomFastWords> getList(RoomFastWordsDto roomFastWordsDto);

}
