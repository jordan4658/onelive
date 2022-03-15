package com.onelive.api.service.live;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.RoomFastWords;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-19
 */
public interface RoomFastWordsService extends IService<RoomFastWords> {

	/**
	 * 查询所有大哥前语言的快捷语
	 * 
	 * @return
	 */
	List<RoomFastWords> getAll();

}
