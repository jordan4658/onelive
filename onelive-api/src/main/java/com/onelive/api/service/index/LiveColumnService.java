package com.onelive.api.service.index;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.index.LiveColumnVO;
import com.onelive.common.mybatis.entity.LiveColumn;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-20
 */
public interface LiveColumnService extends IService<LiveColumn> {

	/**
	 * 	查询所有分类
	 * @return
	 */
	List<LiveColumnVO> getAll(String lang);

}
