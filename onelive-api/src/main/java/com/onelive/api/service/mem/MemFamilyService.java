package com.onelive.api.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemFamily;

/**
 * <p>
 * 家族表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface MemFamilyService extends IService<MemFamily> {

	/**
	 * 根据用户ID查询家族表
	 * @param familyUserId
	 * @return
	 */
	MemFamily queryByUserId(Long familyUserId);

	
}
