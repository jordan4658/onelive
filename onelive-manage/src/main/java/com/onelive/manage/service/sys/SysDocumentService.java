package com.onelive.manage.service.sys;

import com.onelive.common.mybatis.entity.SysDocument;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章管理表 服务类
 * </p>
 *
 */
public interface SysDocumentService extends IService<SysDocument> {
	public void updateStatus(Long id, String account);
}
