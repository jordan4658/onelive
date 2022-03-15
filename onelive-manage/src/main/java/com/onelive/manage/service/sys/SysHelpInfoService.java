package com.onelive.manage.service.sys;

import com.onelive.common.mybatis.entity.SysHelpInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 帮助中心表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface SysHelpInfoService extends IService<SysHelpInfo> {
	
	void updateStatus(Long id, String account);
}
