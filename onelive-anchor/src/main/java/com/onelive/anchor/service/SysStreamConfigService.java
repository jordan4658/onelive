package com.onelive.anchor.service;

import com.onelive.common.mybatis.entity.SysStreamConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-01
 */
public interface SysStreamConfigService extends IService<SysStreamConfig> {

	/**
	 *	获取使用中的流服务商
	 * @return
	 */
	SysStreamConfig getUse();

}
