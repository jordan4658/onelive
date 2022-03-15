package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
public interface MemUserService extends IService<MemUser> {

	/**
	 * 根据用户id查询用表基本信息
	 * @param userId
	 * @return
	 */
	MemUser queryById(Long userId);
}
